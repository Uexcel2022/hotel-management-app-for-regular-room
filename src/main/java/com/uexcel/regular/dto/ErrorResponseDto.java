package com.uexcel.regular.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponseDto {
    private  String timestamp;
    private int status;
    private  String error;
    private  String message;
    private  String apiPath;
}
