package com.binarylab.rafroid.util;

import android.content.Intent;

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
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        return calendar.getTime();
    }

    public static String parseTime(String date_time, boolean isStart){

        int hours;
        int minutes;

        if(isStart){
            String tmp = date_time.split("-")[0];
            hours = Integer.valueOf(tmp.substring(0,tmp.indexOf(":")));
            minutes = Integer.valueOf(tmp.substring(tmp.length()-2, tmp.length()));
        }else{
            hours = Integer.valueOf(date_time.split("-")[1]);
            minutes = 0;
        }

        return hours + ":" + minutes;
    }

    public static Date parseDate(String date){

        int year = Integer.valueOf(date.split("\\.")[2]);
        int month = Integer.valueOf(date.split("\\.")[1]);
        int day = Integer.valueOf(date.split("\\.")[0]);

        Calendar calendar =  Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        return calendar.getTime();
    }

    public static Date parseTimeHrs(String time, boolean isStart){
        int hours;
        int minutes = 0;

        if(isStart)
            hours = Integer.valueOf(time.split("-")[0]);
        else
            hours = Integer.valueOf(time.split("-")[1]);

        Calendar calendar =  Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);

        return calendar.getTime();
    }


}
