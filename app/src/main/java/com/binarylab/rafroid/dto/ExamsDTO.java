package com.binarylab.rafroid.dto;

import java.util.List;

public class ExamsDTO {

    private String version;
    private List<ExamDTO> schedule;
    private String error;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<ExamDTO> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<ExamDTO> schedule) {
        this.schedule = schedule;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
