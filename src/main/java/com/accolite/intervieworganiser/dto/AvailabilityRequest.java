package com.accolite.intervieworganiser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalTime;

/*
for accepting new availability from front-end and providing access to date and time of availability slots
 */
public class AvailabilityRequest {

    @JsonProperty("start_date")
    @NotEmpty(message = "Please provide availability start date")
    private LocalDate startDate;

    @JsonProperty("end_date")
    @NotEmpty(message = "Please provide availability end date")
    private LocalDate endDate;

    @JsonProperty("start_time")
    @NotEmpty(message = "Please provide availability start time")
    private LocalTime startTime;

    @JsonProperty("end_time")
    @NotEmpty(message = "Please provide availability end time")
    private LocalTime endTime;

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public AvailabilityRequest(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        this.startDate=startDate;
        this.endDate=endDate;
        this.startTime=startTime;
        this.endTime=endTime;
    }
}
