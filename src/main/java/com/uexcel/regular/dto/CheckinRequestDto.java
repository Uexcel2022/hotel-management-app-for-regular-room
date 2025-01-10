package com.uexcel.regular.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CheckinRequestDto {
    @NotNull(message = "Room number is required.")
    @Pattern(regexp = "R[0-9]{3}",message = "In valid room number.")
    private  String roomNumber;
    @Positive(message = "Number of days must be greater than zero.")
    @NotNull(message = "Number of days can not be null.")
    private Integer numberOfDays;
    @NotNull(message = "Name is required.")
    @Pattern(regexp = "[a-zA-Z]{3,} [a-zA-Z]{3,}",message = "Expecting 2  names and not less than 3 letters.")
    private String name;
    @NotNull(message = "Name is required.")
    @Pattern(regexp = "0[7-9][01][0-9]{8}", message = "Invalid phone number.")
    private String phone;
}
