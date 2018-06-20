package com.binarylab.rafroid.util;

import com.binarylab.rafroid.model.DayOfWeek;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static Date parseDateAndTime(String date_time, boolean isStart){
        int day = Integer.valueOf(date_time.split("\\.")[0]);
        int month = Integer.valueOf(date_time.split("\\.")[1].substring(0, 2)) - 1;

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
        calendar.set(Calendar.MINUTE, 0);
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

        String hourString = hours + "";
        if (hourString.length() == 1)
            hourString = "0" + hours;

        String minuteString = "" + minutes;
        if (minuteString.length() == 1)
            minuteString = "0" + minutes;

        return hourString + ":" + minuteString;
    }

    public static Date parseDate(String date){

        int year = Integer.valueOf(date.split("\\.")[2]);
        int month = Integer.valueOf(date.split("\\.")[1]) - 1;
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


    public static DayOfWeek getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        DayOfWeek day = null;

        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                day = DayOfWeek.PON;
                break;
            case Calendar.TUESDAY:
                day = DayOfWeek.UTO;
                break;
            case Calendar.WEDNESDAY:
                day = DayOfWeek.SRE;
                break;
            case Calendar.THURSDAY:
                day = DayOfWeek.ČET;
                break;
            case Calendar.FRIDAY:
                day = DayOfWeek.PET;
                break;
            case Calendar.SATURDAY:
                day = DayOfWeek.SUB;
                break;
            case Calendar.SUNDAY:
                day = DayOfWeek.NED;
                break;
            default:
                break;
        }

        return day;
    }

    public static Date getZeroTimeDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        date = calendar.getTime();
        return date;
    }
}
