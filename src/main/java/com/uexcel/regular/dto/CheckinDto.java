package com.uexcel.regular.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter @Setter
public class CheckinDto {
    @NotNull(message = "Room number is required.")
    @Pattern(regexp = "R[0-9]{3}",message = "In valid room number.")
    private  String roomNumber;
    private Date dateIn;
    private Date dateOut;
    @NotNull(message = "Name is required.")
    @Pattern(regexp = "[a-zA-Z]{3,} [a-zA-Z]{3,}",message = "Expecting 2  names and not less than 3 letters.")
    private String name;
    @NotNull(message = "Name is required.")
    @Pattern(regexp = "0[7-9][01][0-9]{8}", message = "Invalid phone number.")
    private String phone;
}
