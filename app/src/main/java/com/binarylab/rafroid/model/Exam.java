package com.binarylab.rafroid.model;

import java.util.Date;
import io.realm.RealmObject;

public class Exam extends RealmObject{

    private String testName;
    private Date time;
    private String classroom;
    private String type;

    //Getters & Setters
    //--------------------------------------------------------------------------------------------//
    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
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

    public ExamType getType() {
        return ExamType.valueOf(type);
    }

    public void setType(ExamType type) {
        this.type = type.toString();
    }
}
