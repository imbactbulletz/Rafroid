package com.binarylab.rafroid.model;

import java.util.Date;

import io.realm.RealmObject;

public class Calender extends RealmObject{

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

    public CalenderType getType() {
        return CalenderType.valueOf(type);
    }

    public void setType(CalenderType type) {
        this.type = type.toString();
    }
}
