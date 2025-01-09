package com.uexcel.regular.controller;

import com.uexcel.regular.dto.ReservedRoomInFoDto;
import com.uexcel.regular.exception.AppExceptions;
import com.uexcel.regular.service.IRegularRoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/regular")
public class RegularRoomController {
    private final IRegularRoomService regularRoomService;
    @GetMapping("reservation/room")
    public ResponseEntity<ReservedRoomInFoDto> getRegularRoomById(
            @RequestParam String roomNumber) {
        ReservedRoomInFoDto regularRoom =
                regularRoomService.getRegularRoomByRoomNumber(roomNumber);
        if (regularRoom.getId()==null) {
            throw new AppExceptions(HttpStatus.NOT_FOUND.value(),
                    "Not Found","No reservation found for room number: " + roomNumber
            );
        }
        return new ResponseEntity<>(regularRoom, HttpStatus.OK);
    }
}
