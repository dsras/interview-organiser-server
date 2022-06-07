package com.accolite.intervieworganiser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Stores information about new availabilities with set time range over one or more days.
 * <p>Availability data transfer object that stores information about a range of availabilities
 * provided in one request.</p>
 */
public class AvailabilityRangeRequest {

    @JsonProperty("startTime")
    @NotNull(message = "Please provide a start time")
    private LocalTime startTime;

    @JsonProperty("endTime")
    @NotNull(message = "Please provide an end time")
    private LocalTime endTime;

    @JsonProperty("dates")
    @NotNull(message = "Please provide a start date")
    private LocalDate[] dates;
    /**
     * Gets availability range start date.
     *
     * @return the availability range start date
     */
    public LocalDate[] getDates() {
        return dates;
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
     * @param dates the availability range dates
     * @param startTime the availability start time
     * @param endTime the availability end time
     */
    public AvailabilityRangeRequest(
        LocalDate[] dates,
        LocalTime startTime,
        LocalTime endTime
    ) {
        Objects.requireNonNull(dates, "dates must not be null");
        Objects.requireNonNull(startTime, "start time must not be null");
        Objects.requireNonNull(endTime, "end time must not be null");

        this.dates = dates;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
