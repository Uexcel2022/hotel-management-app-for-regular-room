package com.uexcel.regular.service.impl;

import com.uexcel.regular.constants.Month;
import com.uexcel.regular.dto.*;
import com.uexcel.regular.exception.AppExceptions;
import com.uexcel.regular.model.Reservation;
import com.uexcel.regular.model.ReservationDates;
import com.uexcel.regular.persistence.ReservationDateRepository;
import com.uexcel.regular.persistence.ReservationRepository;
import com.uexcel.regular.service.IReservationService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
@AllArgsConstructor
public class IReservationServiceImpl implements IReservationService {
    private  final ReservationRepository reservationRepository;
    private  final ReservationDateRepository reservationDateRepository;
    private final Month month;
    private final Environment environment;
    private final Logger logger = LoggerFactory.getLogger(IReservationServiceImpl.class);
    @Override
    public List<FreeRoomsDto> getFreeRoomsByMonth(String monthNAme) {
        if(environment.getProperty("NUMBER_OF_ROOMS")==null){
            throw  new RuntimeException("Environment property 'NUMBER_OF_ROOMS' not set.");
        }
        int numberOfRooms =Integer.parseInt(environment.getProperty("NUMBER_OF_ROOMS"));
        List<Reservation> reservations = reservationRepository.findAll();
        List<LocalDate> reservedDates = new ArrayList<>();
        List<FreeRoomsDto> freeRoomsDtoList =  new ArrayList<>();
        List<LocalDate> monthDates = new ArrayList<>();
        for(Reservation reservation : reservations){
            reservation.getReservationDates()
                    .forEach(reservedDate->reservedDates.add(reservedDate.getDate()));
        }
        reservedDates.sort(LocalDate::compareTo);
        LocalDate monthStartDate = month.getStartDate(monthNAme);
        for(int i = 0; i < reservedDates.size(); i++){
            LocalDate monthDate = reservedDates.get(i);
            if(monthDate.getMonth().equals(monthStartDate.getMonth())){
                monthDates.add(monthDate);
            }
        }
        int mothLength = monthStartDate.lengthOfMonth();
        for(int i = 0; i < mothLength; i ++){
            LocalDate date = monthStartDate.plusDays(i);
            if(monthDates.contains(date) && !freeRoomsDtoList.contains(date) && (date.equals(LocalDate.now())|| date.isAfter(LocalDate.now()))){
                int numberOfRoomsReserved = monthDates.stream().filter(present->present.equals(date)).toList().size();
                int freeRooms = numberOfRooms - numberOfRoomsReserved;
                if(freeRooms > 0) {
                    freeRoomsDtoList.add(new FreeRoomsDto(date,freeRooms));
                }
            } else {
                if(!freeRoomsDtoList.contains(date) && (date.equals(LocalDate.now())|| date.isAfter(LocalDate.now()))){
                    freeRoomsDtoList.add(new FreeRoomsDto(date,  numberOfRooms));
                }
            }
        }
        return freeRoomsDtoList;
    }

    @Override
    public List<FreeRoomsDto> getFreeRoomsByDays(Integer numberOfDays) {
        if(environment.getProperty("NUMBER_OF_ROOMS")==null){
            throw  new AppExceptions(HttpStatus.EXPECTATION_FAILED.value(),
                    "Fail", "Environment property 'NUMBER_OF_ROOMS' not set.");
        }
        int numberOfRooms =Integer.parseInt(environment.getProperty("NUMBER_OF_ROOMS"));
        List<Reservation> reservations = reservationRepository.findAll();
        if(numberOfDays == null){
            numberOfDays = 60;
        }
        List<LocalDate> reserveDates = new ArrayList<>();
        List<FreeRoomsDto> freeRoomsDtoList =  new ArrayList<>();
        for(Reservation reservation : reservations){
            reservation.getReservationDates()
                    .forEach(bd-> reserveDates.add(bd.getDate()));
        }
        reserveDates.sort(LocalDate::compareTo);

        for(int i = 0; i < numberOfDays; i++){
            LocalDate date = LocalDate.now().plusDays(i);
            if(!freeRoomsDtoList.contains(date) && reserveDates.contains(date) && (date.equals(LocalDate.now())|| date.isAfter(LocalDate.now()))){
                int numberOfRoomsReserved = (int) reserveDates.stream().filter(present->present.equals(date)).toList().size();
                int freeRooms = numberOfRooms - numberOfRoomsReserved;
                if(freeRooms > 0) {
                    freeRoomsDtoList.add(new FreeRoomsDto(date,freeRooms));
                }
            } else {
                if((date.equals(LocalDate.now()) || date.isAfter(LocalDate.now())) && !freeRoomsDtoList.contains(date)){
                    freeRoomsDtoList.add(new FreeRoomsDto(date,  numberOfRooms));
                }
            }
        }
        return freeRoomsDtoList;
    }

    @Override
    @Transactional
    public ReservationResponseDto saveReservation(ReservationDto reservationDto) {
        if(environment.getProperty("NUMBER_OF_ROOMS")==null){
            throw  new RuntimeException("Environment property 'NUMBER_OF_ROOMS' not set.");
        }
        int numberOfRooms =Integer.parseInt(environment.getProperty("NUMBER_OF_ROOMS"));
        List<FreeRoomsDto> unAvailableDates = new ArrayList<>();
        DateRoomsDto booking;
        for(int i = 0; i < reservationDto.getDates().size();i++){
            booking = reservationDto.getDates().get(i);
           List<ReservationDates> reservedDates =
                   reservationDateRepository.findByDate(booking.getDate());
           if(reservedDates == null){
               continue;
           }
           int reservations = reservedDates.size();
           int  intendToBeReserved = booking.getNumberOfRooms();
           if(numberOfRooms - (reservations+intendToBeReserved) < 0){
               unAvailableDates.add(
                       new FreeRoomsDto(booking.getDate(),(numberOfRooms-reservations)));
           }
        }

        if(!unAvailableDates.isEmpty()){
            ReservationResponseDto rDto = new ReservationResponseDto(
                    HttpStatus.BAD_REQUEST.value(), "Bad Request",
                    "Available number of rooms exceeded.");
            rDto.getInfo().addAll(unAvailableDates);
            return rDto;
        }
       Reservation reservation = new Reservation();
        reservation.setName(reservationDto.getName());
        reservation.setPhone(reservationDto.getPhone());
        reservation.setDescription("regular");
       Reservation savedReservation = reservationRepository.save(reservation);
       if(savedReservation.getId() == null){
           throw new AppExceptions(HttpStatus.EXPECTATION_FAILED.value(),
                   "Fail", "Fail to save reservation.");
       }
        List<ReservationDates> reservationDates = new ArrayList<>();
        reservationDto.getDates().forEach(res -> {
            for(int i = 0;i< res.getNumberOfRooms();i++) {
                ReservationDates reservationDate = new ReservationDates();
                reservationDate.setDate(res.getDate());
                reservationDate.setReservation(savedReservation);
                reservationDates.add(reservationDate);
            }
        });
      List<ReservationDates> savedResDates =  reservationDateRepository.saveAll(reservationDates);
        for(ReservationDates rs : savedResDates){
            if(rs.getId() ==null){
                throw new AppExceptions(HttpStatus.EXPECTATION_FAILED.value(),
                        "Fail", "Fail to save reservation dates");
            }
        }
        return new ReservationResponseDto(
                HttpStatus.CREATED.value(), "Created",
                "Reservation created successfully.");
    }

    @Override
    public Reservation findReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(()->new AppExceptions(HttpStatus.NOT_FOUND.value(),
                        "Not Found","Reservation not found for the given id: "+id));
    }

    @Override
    public ResponseDto deletePastReservations(){
            List<Reservation> reservations = reservationRepository.findAll();
            if (reservations.isEmpty()) {
                return new ResponseDto(HttpStatus.NOT_FOUND.value(),
                        "Not Found", "No reservation found");
            }
            for (Reservation r : reservations) {
                List<ReservationDates> reservationDatesDate = r.getReservationDates();
                int rSize = reservationDatesDate.size();
                if(rSize==0){
                    reservationRepository.deleteById(r.getId());
                }
                for (ReservationDates resDate : reservationDatesDate) {
                    if (LocalDate.now().isAfter(resDate.getDate())) {
                        reservationDateRepository.deleteById(resDate.getId());
                            if(rSize==1){
                                reservationRepository.deleteById(r.getId());
                            }
                    }
                }
            }
        return new ResponseDto(HttpStatus.OK.value(),
                "Ok", "Reservation deleted successfully.");
    }

}
