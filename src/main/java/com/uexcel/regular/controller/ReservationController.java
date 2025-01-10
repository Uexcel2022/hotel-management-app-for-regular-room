package com.uexcel.regular.controller;

import com.uexcel.regular.dto.FreeRoomsDto;
import com.uexcel.regular.dto.ReservationDto;
import com.uexcel.regular.dto.ReservationResponseDto;
import com.uexcel.regular.dto.ResponseDto;
import com.uexcel.regular.service.IReservationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/regular", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReservationController {
    private final IReservationService reservationService;

    @GetMapping("/days")
    public ResponseEntity<List<FreeRoomsDto>> roomCalendar(
            @RequestParam(required = false ) Integer numberOfDays){
        List<FreeRoomsDto> freeRoomsDtoList =
                reservationService.getFreeRoomsByDays(numberOfDays);
        return ResponseEntity.ok(freeRoomsDtoList);
    }

    @GetMapping("/month")
    public ResponseEntity<List<FreeRoomsDto>> roomCalendar(@RequestParam(required = false) String monthName){
        List<FreeRoomsDto> freeRoomsDtoList = reservationService.getFreeRoomsByMonth(monthName);
        return ResponseEntity.ok(freeRoomsDtoList);
    }
    @PostMapping("/reservation")
    public ResponseEntity<ReservationResponseDto> saveReservation(@RequestBody @Valid ReservationDto reservationDto){
      ReservationResponseDto rRDto =  reservationService.saveReservation(reservationDto);
      return ResponseEntity.status(rRDto.getStatus()).body(rRDto);
    }

    @DeleteMapping("/reservation")
    private ResponseEntity<ResponseDto> deletePastReservations(){
        ResponseDto rs = reservationService.deletePastReservations();
        return  ResponseEntity.status(rs.getStatus()).body(rs);
    }

}
