package com.uexcel.regular.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
public class FreeRoomsDto {
    private LocalDate date;
    private  int availableRooms;
}
