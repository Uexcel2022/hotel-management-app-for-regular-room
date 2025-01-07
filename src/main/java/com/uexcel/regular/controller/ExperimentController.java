package com.uexcel.regular.controller;

import com.uexcel.regular.constants.Month;
import com.uexcel.regular.dto.AvailableRoomsDto;
import com.uexcel.regular.model.RegularRoom;
import com.uexcel.regular.model.ReservationDates;
import com.uexcel.regular.persistence.RegularRoomRepository;
import com.uexcel.regular.service.IRegularRoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/free")
@AllArgsConstructor
public class ExperimentController {
    private final IRegularRoomService regularRoomService;
    @GetMapping("/room/num-of-days")
    public ResponseEntity<Map<String,List<AvailableRoomsDto>>> getFreeRoomsByDays(
            @RequestParam(required = false) Integer numberOfDays) {
        Map<String,List<AvailableRoomsDto>> sortedResult  =
                regularRoomService.getFreeRoomsByDays(numberOfDays);
        return new ResponseEntity<>(sortedResult, HttpStatus.OK);
    }

    @GetMapping("/room/month")
    public ResponseEntity<Map<String,List<AvailableRoomsDto>>> getFreeRoomsByMonth(
            @RequestParam(required = false) String monthName) {
        Map<String,List<AvailableRoomsDto>> sortedResult =
                regularRoomService.getFreeRoomsByMonth(monthName);
        return new ResponseEntity<>(sortedResult, HttpStatus.OK);
    }

}
