package com.uexcel.regular.service.impl;

import com.uexcel.regular.constants.Constants;
import com.uexcel.regular.dto.CheckinRequestDto;
import com.uexcel.regular.dto.CheckinResponseDto;
import com.uexcel.regular.dto.ResponseDto;
import com.uexcel.regular.exception.AppExceptions;
import com.uexcel.regular.mapper.CheckinMapper;
import com.uexcel.regular.model.Checkin;
import com.uexcel.regular.model.RegularRoom;
import com.uexcel.regular.persistence.CheckinRepository;
import com.uexcel.regular.persistence.RegularRoomRepository;
import com.uexcel.regular.service.ICheckinService;
import com.uexcel.regular.service.IRegularRoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class ICheckinImpl implements ICheckinService {
    private  final CheckinMapper checkinMapper;
    public final IRegularRoomService regularRoomService;
    private final CheckinRepository checkinRepository;
    private  final RegularRoomRepository regularRoomRepository;
    @Override
    public ResponseDto checkin(CheckinRequestDto checkinRequestDto) {
        RegularRoom regularRoom = new RegularRoom();
//                regularRoomService.getRegularRoomByRoomNumber(checkinRequestDto.getRoomNumber());

        Checkin notCheckin = checkinRepository
                .findByRegularRoom_RoomNumberAndDateOut(checkinRequestDto.getRoomNumber(), null);
        if (notCheckin != null) {
            throw  new AppExceptions(
                    HttpStatus.BAD_REQUEST.value(),
                    Constants.BadRequest,String.format("Room %s is in use.", checkinRequestDto.getRoomNumber())
            );
        }
        Checkin checkin =  checkinMapper.toCheckin(new Checkin(), checkinRequestDto);
        checkin.setAmount(regularRoom.getPrice());
        checkin.setRegularRoom(regularRoom);
        Checkin savedCheckin =  checkinRepository.save(checkin);
        if(savedCheckin.getId() == null) {
            throw  new AppExceptions(
                    HttpStatus.EXPECTATION_FAILED.value(),Constants.Failed,"Checkin failed."
            );
        }
        return new ResponseDto(HttpStatus.CREATED.value(), Constants.Created,"Checkin  successfully.");
    }

    @Override
    public ResponseDto checkout(Long checkinId) {
        Checkin toUpdate = checkinRepository.findById(checkinId)
                .orElseThrow(() -> new AppExceptions(HttpStatus.BAD_REQUEST.value(),
                    Constants.BadRequest, String.format("No checkin found for CheckId: ", checkinId)));

        if(toUpdate.getDateOut()!=null){
            throw  new AppExceptions(
                    HttpStatus.BAD_REQUEST.value(),
                    Constants.BadRequest,String.format("Room %s is not in use.",
                    toUpdate.getRegularRoom().getRoomNumber())
            );
        }
        toUpdate.setDateOut(ICheckinService.getTime());
       Checkin update = checkinRepository.save(toUpdate);
       if(update.getDateOut()==null){
           throw  new AppExceptions(
                   HttpStatus.EXPECTATION_FAILED.value(),Constants.Failed,"Checkout failed."
           );
       }
        return new ResponseDto(HttpStatus.OK.value(), Constants.OK,"Checkout successfully.");
    }

    @Override
    public List<CheckinResponseDto> getCheckin(String roomNumber) {
        if(roomNumber != null) {
            if(!regularRoomRepository.existsByRoomNumber(roomNumber)) {
                throw new AppExceptions(HttpStatus.NOT_FOUND.value(),
                        Constants.NotFound,String.format("No room found with roomNumber: %s", roomNumber)
                );
            }
         Checkin toCheckoutRoom =  checkinRepository
                 .findByRegularRoom_RoomNumberAndDateOut(roomNumber,null);
                 if(toCheckoutRoom == null) {
                     throw new AppExceptions(HttpStatus.BAD_REQUEST.value(),
                             Constants.BadRequest, String.format("Room %s is not in use.", roomNumber));
                 }
          return  List.of(checkinMapper.toCheckinResponseDto(toCheckoutRoom,new CheckinResponseDto()));
        }
        List<Checkin> checkins = checkinRepository.findByDateOut(null);
        List<CheckinResponseDto> checkinResponseDtoList = new ArrayList<>();
        if(checkins == null) {
            throw  new AppExceptions(
                    HttpStatus.NOT_FOUND.value(),
                    Constants.NotFound,"No checkin found."
            );
        }
        checkins.forEach(checkin -> checkinResponseDtoList
                .add(checkinMapper.toCheckinResponseDto(checkin,new CheckinResponseDto())));
        return checkinResponseDtoList;
    }
}
