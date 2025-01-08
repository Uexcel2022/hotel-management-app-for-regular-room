package com.uexcel.regular.controller;

import com.uexcel.regular.dto.CheckinRequestDto;
import com.uexcel.regular.dto.CheckinResponseDto;
import com.uexcel.regular.dto.ResponseDto;
import com.uexcel.regular.service.ICheckinService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping(value = "/api/checkin",produces = MediaType.APPLICATION_JSON_VALUE)
public class CheckinController {
    private  final ICheckinService checkinService;
    @PostMapping
    public ResponseEntity<ResponseDto> checkin(@Valid @RequestBody CheckinRequestDto checkinRequestDto) {
        ResponseDto rs = checkinService.checkin(checkinRequestDto);
        return new ResponseEntity<>(rs, HttpStatus.CREATED);
    }
    @PutMapping("/{checkinId}")
    public ResponseEntity<ResponseDto> checkout(@PathVariable Long checkinId) {
        ResponseDto rs = checkinService.checkout(checkinId);
        return new ResponseEntity<>(rs, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CheckinResponseDto>> getCheckins(
            @RequestParam(required = false) String roomNumber){
        List<CheckinResponseDto> result = checkinService.getCheckin(roomNumber);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

}
