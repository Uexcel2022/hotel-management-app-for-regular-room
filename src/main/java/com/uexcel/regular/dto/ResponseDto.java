package com.uexcel.regular.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseDto {
    private int status;
    private String description;
    private String message;
}
