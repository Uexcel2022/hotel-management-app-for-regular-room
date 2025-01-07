package com.uexcel.regular.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
public class AvailableRoomsDto {
    private LocalDate date;
    private String Rooms;
}
