package com.binarylab.rafroid.model;

import java.time.DayOfWeek;
import java.util.Date;

import io.realm.RealmObject;

public class Consultation extends RealmObject {

    private String day;
    private Date startTime;
    private Date endTime;
    private String lecturer;
    private String className;
    private String classroom;

    //Getters & Setters//
    //--------------------------------------------------------------------------------------------//
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }
}
