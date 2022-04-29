package com.accolite.intervieworganiser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Stores information about new availabilities with set time range over one or more days.
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

    /**
     * Gets availability range start date.
     *
     * @return the availability range start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Gets availability range end date.
     *
     * @return the availability range end date
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Gets availability start time.
     *
     * @return the availability start time
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Gets the availability end time.
     *
     * @return the availability end time
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Parameterised constructor.
     *
     * @param startDate the availability range start date
     * @param endDate the availability range end date
     * @param startTime the availability start time
     * @param endTime the availability end time
     */
    public AvailabilityRequest(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        this.startDate=startDate;
        this.endDate=endDate;
        this.startTime=startTime;
        this.endTime=endTime;
    }
}
