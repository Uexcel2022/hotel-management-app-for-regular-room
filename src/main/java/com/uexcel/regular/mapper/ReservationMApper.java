package com.uexcel.regular.mapper;

import com.uexcel.regular.dto.ReservationDto;
import com.uexcel.regular.model.Reservation;
import com.uexcel.regular.model.ReservationDates;

import java.util.ArrayList;
import java.util.List;

public class ReservationMApper {
    public Reservation toReservation(Reservation reservation, ReservationDto reservationDto) {
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
