package com.uexcel.regular.controller;

import com.uexcel.regular.constants.Constants;
import com.uexcel.regular.dto.ResponseDto;
import com.uexcel.regular.model.RegularRoom;
import com.uexcel.regular.model.Reservation;
import com.uexcel.regular.model.ReservationDates;
import com.uexcel.regular.persistence.ReservationDateRepository;
import com.uexcel.regular.persistence.ReservationRepository;
import com.uexcel.regular.service.IReservationDatesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/reservation/date",produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ReservationDatesController {
    private final IReservationDatesService reservationDatesService;
    private final ReservationRepository reservationRepository;
    private  final ReservationDateRepository reservationDateRepository;
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> getReservationDates(@PathVariable("id") Long id){
        reservationDatesService.deleteReservationDateById(id);
        return new ResponseEntity<>(new ResponseDto(HttpStatus.OK.value(),
                Constants.OK,"Reservation deleted successfully."),HttpStatus.OK);
    }

    @GetMapping("/{phoneNumber}")
    public ResponseEntity<List<ReservationDates>> getReservationDates(
            @PathVariable("phoneNumber") String phoneNumber ){
       List<ReservationDates> reservationDates = reservationDateRepository.findByPhoneNumber(phoneNumber);
        return new ResponseEntity<>(reservationDates,HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateReservationDates(@RequestBody List<ReservationDates> reservationDates ){
        Reservation reservation = reservationDates.getFirst().getReservation();
        List<ReservationDates> rsvDate = new ArrayList<>();
        reservationDates.forEach(reservationDate -> {
            ReservationDates dt = new ReservationDates();
            dt.setId(reservationDate.getId());
            dt.setDate(reservationDate.getDate());
            dt.setReservation(reservation);
            dt.setRegularRoom(reservationDate.getRegularRoom());
            rsvDate.add(dt);
        });
        reservationDateRepository.saveAll(rsvDate);
        return new ResponseEntity<>(new ResponseDto(HttpStatus.OK.value(),
                Constants.OK,"Reservation updated successfully."),HttpStatus.OK);
    }

}
