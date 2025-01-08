package com.uexcel.regular.mapper;

import com.uexcel.regular.dto.ReservationDto;
import com.uexcel.regular.model.ReservationDates;

import java.util.ArrayList;
import java.util.List;

public class Reservation {
    public com.uexcel.regular.model.Reservation toReservation(com.uexcel.regular.model.Reservation reservation, ReservationDto reservationDto) {
        reservation.setName(reservationDto.getName());
        reservation.setPhone(reservationDto.getPhone());
        List<ReservationDates> reservationDates = new ArrayList<>();
        reservationDto.getDates().forEach(res -> {
            for(int i = 0; i< res.getRooms().size(); i++) {
            }
        });
        return  reservation;
    }
}
