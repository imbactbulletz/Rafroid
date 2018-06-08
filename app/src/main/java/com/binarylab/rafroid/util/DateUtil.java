package com.binarylab.rafroid.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static Date parseDateAndTime(String date_time, boolean isStart){
        int day = Integer.valueOf(date_time.split("\\.")[0]);
        int month = Integer.valueOf(date_time.split("\\.")[1].substring(0,2));

        int hour;
        if(isStart){
            String tmp = date_time.split("-")[0];
            tmp = tmp.substring(tmp.length()-2);
            hour = Integer.valueOf(tmp);
        }else{
            hour = Integer.valueOf(date_time.split("-")[1]);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR, hour);
        return calendar.getTime();
    }

}
