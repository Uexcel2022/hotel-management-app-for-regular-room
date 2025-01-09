package com.uexcel.regular.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter @AllArgsConstructor
public class ReservationDatesDto {
    @NotNull
    @NotEmpty
    private Long id;
    @FutureOrPresent
    private LocalDate date;
}
