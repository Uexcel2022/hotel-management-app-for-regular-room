package com.uexcel.regular.mapper;

import com.uexcel.regular.dto.CheckinDto;
import com.uexcel.regular.model.Checkin;
import org.springframework.stereotype.Component;
import static com.uexcel.regular.service.ICheckinService.getTime;

@Component
public class CheckinMapper {
    public Checkin toCheckin(Checkin checkin, CheckinDto checkinDto) {
        checkin.setName(checkinDto.getName());
        checkin.setPhone(checkinDto.getPhone());
        checkin.setDateIn(getTime());
        return checkin;
    }
}
