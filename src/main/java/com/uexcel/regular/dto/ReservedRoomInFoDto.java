package com.uexcel.regular.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ReservedRoomInFoDto {
    @NotNull
    @NotEmpty
    private Long id;
    @NotNull
    @NotEmpty
    private String roomNumber;
    @Positive
    private double price;
    private List<ReservationDetailsDto> reservationDetails = new ArrayList<>();
}
