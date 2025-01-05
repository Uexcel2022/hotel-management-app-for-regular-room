package com.uexcel.regular.service.impl;

import com.uexcel.regular.constants.Month;
import com.uexcel.regular.controller.RegularRoomController;
import com.uexcel.regular.dto.FreeRoomsDto;
import com.uexcel.regular.exception.AppExceptions;
import com.uexcel.regular.model.Reservation;
import com.uexcel.regular.persistence.ReservationRepository;
import com.uexcel.regular.service.IReservationService;
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
    private  final Logger logger = LoggerFactory.getLogger(RegularRoomController.class);
    private  final ReservationRepository reservationRepository;
    private final Month month;
    private final Environment environment;
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
            reservation.getBookDates().stream()
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
                int numberOfRoomsReserved = (int) monthDates.stream().filter(present->present.equals(date)).toList().stream().count();
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
    public List<FreeRoomsDto> getFreeRoomsByDays(Integer days) {
        if(environment.getProperty("NUMBER_OF_ROOMS")==null){
            throw  new AppExceptions(HttpStatus.EXPECTATION_FAILED.value(),
                    "Fail", "Environment property 'NUMBER_OF_ROOMS' not set.");
        }
        int numberOfRooms =Integer.parseInt(environment.getProperty("NUMBER_OF_ROOMS"));
        List<Reservation> reservations = reservationRepository.findAll();
        if(days == null){
            days = 60;
        }
        List<LocalDate> reserveDates = new ArrayList<>();
        List<FreeRoomsDto> freeRoomsDtoList =  new ArrayList<>();
        for(Reservation reservation : reservations){
            reservation.getBookDates().stream()
                    .forEach(bd-> reserveDates.add(bd.getDate()));
        }
        reserveDates.sort(LocalDate::compareTo);

        for(int i = 0; i < days; i++){
            LocalDate date = LocalDate.now().plusDays(i);
            if(!freeRoomsDtoList.contains(date) && reserveDates.contains(date) && (date.equals(LocalDate.now())|| date.isAfter(LocalDate.now()))){
                int numberOfRoomsReserved = (int) reserveDates.stream().filter(present->present.equals(date)).toList().stream().count();
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
}
