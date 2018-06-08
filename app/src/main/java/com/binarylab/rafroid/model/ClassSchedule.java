package com.binarylab.rafroid.model;

import java.util.Date;

import io.realm.RealmObject;

public class ClassSchedule extends RealmObject {

    private String className;
    private String dayOfWeek;
    private Date time;
    private String classroom;
    private String lecturer;
    private String studentGroups;
    private String type;

    //Getters && Setters//
    //--------------------------------------------------------------------------------------------//
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public DayOfWeek getDayOfWeek() {

        return DayOfWeek.valueOf(dayOfWeek);
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek.toString();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(String studentGroups) {
        this.studentGroups = studentGroups;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
