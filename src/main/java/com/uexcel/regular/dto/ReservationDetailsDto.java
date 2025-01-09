package com.uexcel.regular.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class ReservationDetailsDto {
    private ReservationDatesDto reservedDate;
    private ReservationInfoDto reservation;
}
