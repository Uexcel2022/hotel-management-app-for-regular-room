package com.uexcel.regular.service;

import com.uexcel.regular.model.Reservation;

public interface IReservationDatesService {
    void deleteReservationDateById(Long reservationDateId) ;
    Reservation updateReservationDate (String phone) ;
}
