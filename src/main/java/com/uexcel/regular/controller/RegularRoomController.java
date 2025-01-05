package com.uexcel.regular.controller;

import com.uexcel.regular.dto.FreeRoomsDto;
import com.uexcel.regular.model.Reservation;
import com.uexcel.regular.persistence.ReservationRepository;
import com.uexcel.regular.service.IReservationService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/regular/", produces = MediaType.APPLICATION_JSON_VALUE)
public class RegularRoomController {
    private  final Logger logger = LoggerFactory.getLogger(RegularRoomController.class);
    private  final ReservationRepository reservationRepository;
    private final IReservationService reservationService;

    @GetMapping("/{id}")
    public ResponseEntity<Reservation>bookRegularRoom(@PathVariable Long id){
       Reservation reservations = reservationRepository.findById(id).orElseThrow();
        return ResponseEntity.ok(reservations);
    }
    @GetMapping("/calendar")
    public ResponseEntity<List<FreeRoomsDto>> roomCalendar(
            @RequestParam(required = false ) Integer days){
        List<FreeRoomsDto> freeRoomsDtoList =
                reservationService.getFreeRoomsByDays(days);
        return ResponseEntity.ok(freeRoomsDtoList);
    }

    @GetMapping("/calendar/{monthName}")
    public ResponseEntity<List<FreeRoomsDto>> roomCalendar(@PathVariable String monthName){
        List<FreeRoomsDto> freeRoomsDtoList = reservationService.getFreeRoomsByMonth(monthName);
        return ResponseEntity.ok(freeRoomsDtoList);
    }
}
