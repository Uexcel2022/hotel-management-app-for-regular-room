package com.uexcel.regular.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class ReservationInfoDto {
    private Long id;
    private String name;
    private String phone;
    private String description;
}
