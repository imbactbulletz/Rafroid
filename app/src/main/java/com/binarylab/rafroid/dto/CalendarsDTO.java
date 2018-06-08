package com.binarylab.rafroid.dto;

import java.util.List;

public class CalendarsDTO {

    private int version;
    private List<CalendarDTO> schedule;
    private String error;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<CalendarDTO> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<CalendarDTO> schedule) {
        this.schedule = schedule;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
