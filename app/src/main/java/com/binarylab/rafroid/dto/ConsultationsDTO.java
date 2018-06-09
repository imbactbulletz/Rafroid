package com.binarylab.rafroid.dto;

import java.util.List;

public class ConsultationsDTO {

    private int version;
    private List<ConsultationDTO> schedule;
    private String error;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<ConsultationDTO> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<ConsultationDTO> schedule) {
        this.schedule = schedule;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
