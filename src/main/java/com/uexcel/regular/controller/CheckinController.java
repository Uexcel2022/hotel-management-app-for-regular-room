package com.uexcel.regular.controller;

import com.uexcel.regular.dto.CheckinDto;
import com.uexcel.regular.dto.ResponseDto;
import com.uexcel.regular.service.ICheckinService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping(value = "/api/checkin",produces = MediaType.APPLICATION_JSON_VALUE)
public class CheckinController {
    private  final ICheckinService checkinService;
    @PostMapping
    public ResponseEntity<ResponseDto> checkin(@Valid @RequestBody CheckinDto checkinDto) {
        ResponseDto rs = checkinService.checkin(checkinDto);
        return new ResponseEntity<>(rs, HttpStatus.CREATED);
    }

    @PutMapping("/{roomNumber}")
    public ResponseEntity<ResponseDto> checkout(@PathVariable String roomNumber) {
        ResponseDto rs = checkinService.checkout(roomNumber);
        return new ResponseEntity<>(rs, HttpStatus.CREATED);
    }
}
