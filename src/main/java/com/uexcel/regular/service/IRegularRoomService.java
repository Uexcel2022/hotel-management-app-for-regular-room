package com.uexcel.regular.service;

import com.uexcel.regular.model.RegularRoom;

public interface IRegularRoomService {
    RegularRoom getRegularRoomByRoomNumber(String roomNumber);
}
