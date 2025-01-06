package com.uexcel.regular.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ReservedErrorResponseDto {
    private  String timestamp;
    private int status;
    private  String error;
    private  String message;
    private List<DateRoomsDto> dateRooms;
    private  String apiPath;
}
