package com.uexcel.regular.service;

import com.uexcel.regular.dto.FreeRoomsDto;

import java.util.List;

public interface IReservationService {
    List<FreeRoomsDto> getFreeRoomsByMonth(String monthNAme);
    List<FreeRoomsDto> getFreeRoomsByDays(Integer days);
}
