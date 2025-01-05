package com.uexcel.regular.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
@Getter
public class ReservationResponseDto extends ResponseDto {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    List<FreeRoomsDto> info = new ArrayList<>();
    public ReservationResponseDto(int status, String description, String message) {
        super(status, description, message);
    }
}
