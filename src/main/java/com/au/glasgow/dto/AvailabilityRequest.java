package com.au.glasgow.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;

public class AvailabilityRequest {

    @JsonProperty("date")
    private LocalDate date;
    @JsonProperty("start_time")
    private LocalTime startTime;
    @JsonProperty("end_time")
    private LocalTime endTime;

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public AvailabilityRequest(LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.date = date;
        this.startTime=startTime;
        this.endTime=endTime;
    }
}
