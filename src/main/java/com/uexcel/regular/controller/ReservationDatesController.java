package com.uexcel.regular.controller;

import com.uexcel.regular.constants.Constants;
import com.uexcel.regular.dto.ResponseDto;
import com.uexcel.regular.model.Reservation;
import com.uexcel.regular.model.ReservationDates;
import com.uexcel.regular.persistence.ReservationDateRepository;
import com.uexcel.regular.service.IReservationDatesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/reservation/date",produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ReservationDatesController {
    private final IReservationDatesService reservationDatesService;
    private  final ReservationDateRepository reservationDateRepository;
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> getReservationDates(@PathVariable("id") Long id){
        reservationDatesService.deleteReservationDateById(id);
        return new ResponseEntity<>(new ResponseDto(HttpStatus.OK.value(),
                Constants.OK,"Reservation deleted successfully."),HttpStatus.OK);
    }

    @GetMapping("/{phoneNumber}")
    public ResponseEntity<List<ReservationDates>> getReservationDates(@PathVariable("phoneNumber") String phoneNumber){
       List<ReservationDates> reservationDates = reservationDateRepository.findByPhoneNumber(phoneNumber);
        return new ResponseEntity<>(reservationDates,HttpStatus.OK);
    }

}
