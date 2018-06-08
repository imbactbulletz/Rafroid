package com.binarylab.rafroid.model;

import java.util.Date;
import io.realm.RealmObject;

public class Exam extends RealmObject{

    private String testName;
    private Date startTime;
    private Date endTime;
    private String classroom;
    private String type;
    private String professor;

    //Getters & Setters
    //--------------------------------------------------------------------------------------------//
    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
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

    public String getProfessor() {
        return professor;
    }
    public void setProfessor(String professor) {
        this.professor = professor;
    }
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
