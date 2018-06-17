package com.binarylab.rafroid.util;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;
public class JsonDateDeserializer implements JsonDeserializer<Date> {

    public Date deserialize(JsonElement json, Type date, JsonDeserializationContext context)throws JsonParseException {
        String stringDate = json.getAsJsonPrimitive().getAsString();

        Calendar calendar =Calendar.getInstance();

        String timeString = stringDate.split("T")[1];
        String dateString = stringDate.split("T")[0];

        int year = Integer.valueOf(dateString.split("-")[0]);
        int month = Integer.valueOf(dateString.split("-")[1]) - 1;
        int day = Integer.valueOf(dateString.split("-")[2]);

        int hrs = Integer.valueOf(timeString.split(":")[0]);
        int min = Integer.valueOf(timeString.split(":")[1]);
        int sec = Integer.valueOf(timeString.split(":")[2].substring(0,2));


        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY,hrs);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, sec);
        return calendar.getTime();
    }
}
