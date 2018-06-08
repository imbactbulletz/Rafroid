package com.binarylab.rafroid.dto;

import java.util.List;

public class ClassesDTO {

    private int version;
    private List<ClassDTO> schedule;
    private String error;


    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<ClassDTO> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<ClassDTO> schedule) {
        this.schedule = schedule;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
