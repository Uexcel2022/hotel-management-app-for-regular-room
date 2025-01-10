package com.uexcel.regular.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CheckinResponseDto {
    private Long id;
    private  String roomNumber;
    private String dateIn;
    private String dateOut;
    private Integer numberOfDays;
    private String name;
    private String phone;
    private double amount;
}
