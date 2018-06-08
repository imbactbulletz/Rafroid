package com.binarylab.rafroid.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;
public class JsonDateDeserializer implements JsonDeserializer<Date> {

    public Date deserialize(JsonElement json, Type date, JsonDeserializationContext context)throws JsonParseException {
        String stringDate = json.getAsJsonPrimitive().getAsString();
        return new Date(Long.parseLong(stringDate));
    }
}
