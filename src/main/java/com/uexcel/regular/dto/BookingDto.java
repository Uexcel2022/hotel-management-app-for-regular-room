package com.uexcel.regular.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BookingDto {
    private String name;
    private String roomNumber;
    private List<LocalDate> dates;
}
