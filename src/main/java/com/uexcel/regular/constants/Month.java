package com.uexcel.regular.constants;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
@Component
public class Month {
    public  LocalDate getStartDate(String monthName) {
        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();
        Map<String, LocalDate> monthNames = new HashMap<>();
        monthNames.put("January",LocalDate.of(year,1,1));
        monthNames.put("February",LocalDate.of(year,2,1));
        monthNames.put("March",LocalDate.of(year,3,1));
        monthNames.put("April",LocalDate.of(year,4,1));
        monthNames.put("May",LocalDate.of(year,5,1));
        monthNames.put("June",LocalDate.of(year,6,1));
        monthNames.put("July",LocalDate.of(year,7,1));
        monthNames.put("August",LocalDate.of(year,8,1));
        monthNames.put("September",LocalDate.of(year,9,1));
        monthNames.put("October",LocalDate.of(year,10,1));
        monthNames.put("November",LocalDate.of(year,11,1));
        monthNames.put("December",LocalDate.of(year,12,1));
        return monthNames.get(monthName);
    }
}
