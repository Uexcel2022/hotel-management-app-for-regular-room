package com.uexcel.regular.service.impl;

import com.uexcel.regular.dto.CheckinDto;
import com.uexcel.regular.dto.ResponseDto;
import com.uexcel.regular.exception.AppExceptions;
import com.uexcel.regular.mapper.CheckinMapper;
import com.uexcel.regular.model.Checkin;
import com.uexcel.regular.model.RegularRoom;
import com.uexcel.regular.persistence.CheckinRepository;
import com.uexcel.regular.service.ICheckinService;
import com.uexcel.regular.service.IRegularRoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class ICheckinImpl implements ICheckinService {
    private  final CheckinMapper checkinMapper;
    public final IRegularRoomService regularRoomService;
    private final CheckinRepository checkinRepository;
    @Override
    public ResponseDto checkin(CheckinDto checkinDto) {
        RegularRoom regularRoom =
                regularRoomService.getRegularRoomByRoomNumber(checkinDto.getRoomNumber());

        Checkin notCheckin = checkinRepository
                .findByRegularRoom_RoomNumberAndDateOut(checkinDto.getRoomNumber(), null);
        if (notCheckin != null) {
            throw  new AppExceptions(
                    HttpStatus.BAD_REQUEST.value(),
                    "Bad Request",String.format("Room %s is in use.",checkinDto.getRoomNumber())
            );
        }
        Checkin checkin =  checkinMapper.toCheckin(new Checkin(), checkinDto);
        checkin.setAmount(regularRoom.getPrice());
        checkin.setRegularRoom(regularRoom);
        Checkin savedCheckin =  checkinRepository.save(checkin);
        if(savedCheckin.getId() == null) {
            throw  new AppExceptions(
                    HttpStatus.EXPECTATION_FAILED.value(),"Fail","Checkin failed."
            );
        }
        return new ResponseDto(HttpStatus.CREATED.value(), "Created","Checkin  successfully.");
    }

    @Override
    public ResponseDto checkout(String roomNumber) {
        regularRoomService.getRegularRoomByRoomNumber(roomNumber);
        Checkin checkin = checkinRepository
                .findByRegularRoom_RoomNumberAndDateOut(roomNumber, null);
        if (checkin == null) {
            throw  new AppExceptions(
                    HttpStatus.BAD_REQUEST.value(),
                    "Bad Request",String.format("Room %s is not in use.",roomNumber)
            );
        }
        checkin.setDateOut(ICheckinService.getTime());
       Checkin update = checkinRepository.save(checkin);
       if(update.getDateOut()==null){
           throw  new AppExceptions(
                   HttpStatus.EXPECTATION_FAILED.value(),"Fail","Checkout failed."
           );
       }
        return new ResponseDto(HttpStatus.OK.value(), "Ok","Checkout successfully.");
    }
}
