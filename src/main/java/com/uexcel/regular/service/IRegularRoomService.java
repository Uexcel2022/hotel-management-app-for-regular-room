package com.uexcel.regular.service;

import com.uexcel.regular.dto.AvailableRoomsDto;
import com.uexcel.regular.model.RegularRoom;

import java.util.List;
import java.util.Map;

public interface IRegularRoomService {
    RegularRoom getRegularRoomByRoomNumber(String roomNumber);
    Map<String, List<AvailableRoomsDto>> getFreeRoomsByDays(Integer numberOfDays);
    Map<String,List<AvailableRoomsDto>> getFreeRoomsByMonth(String monthName);
}
