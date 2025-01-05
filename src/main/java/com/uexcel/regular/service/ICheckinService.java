package com.uexcel.regular.service;

import com.uexcel.regular.dto.CheckinDto;
import com.uexcel.regular.dto.ResponseDto;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface ICheckinService {
    ResponseDto checkin(CheckinDto checkinDto);
    ResponseDto checkout(String roomNumber);



    static  String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
