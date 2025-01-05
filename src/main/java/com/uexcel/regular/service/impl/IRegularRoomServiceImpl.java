package com.uexcel.regular.service.impl;

import com.uexcel.regular.exception.AppExceptions;
import com.uexcel.regular.model.RegularRoom;
import com.uexcel.regular.persistence.RegularRoomRepository;
import com.uexcel.regular.service.IRegularRoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IRegularRoomServiceImpl implements IRegularRoomService {
    private final RegularRoomRepository regularRoomRepository;
    @Override
    public RegularRoom getRegularRoomByRoomNumber(String roomNumber) {
        RegularRoom regularRoom =
                regularRoomRepository.findByRoomNumber(roomNumber);
        if (regularRoom == null) {
            throw new AppExceptions(HttpStatus.NOT_FOUND.value(),
                    "Not Fount","No regular room found for room number: " + roomNumber
            );
        }
        return regularRoom;
    }
}
