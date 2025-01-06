package com.uexcel.regular.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class DateRoomsDto {
    private LocalDate date;
    private List<String> Rooms;
}
