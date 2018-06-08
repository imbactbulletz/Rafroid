package com.binarylab.rafroid.model;

import java.util.Date;

import io.realm.RealmObject;

public class Calendar extends RealmObject{

    private Date startDate;
    private Date endDate;
    private String type;

    //Getters & Setters//
    //--------------------------------------------------------------------------------------------//
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public CalendarType getType() {
        return CalendarType.valueOf(type);
    }

    public void setType(CalendarType type) {
        this.type = type.toString();
    }
}
