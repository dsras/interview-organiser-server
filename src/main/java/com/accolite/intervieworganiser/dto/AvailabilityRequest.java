package com.accolite.intervieworganiser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Stores information about new availabilities with set time range over one or more days.
 * <p>Availability data transfer object that stores information about a range of availabilities
 * provided in one request.</p>
 */
public class AvailabilityRequest {

    @JsonProperty("start_date")
    @NotNull(message = "Please provide a start date")
    private LocalDate startDate;

    @JsonProperty("end_date")
    @NotNull(message = "Please provide an end date")
    private LocalDate endDate;

    @JsonProperty("start_time")
    @NotNull(message = "Please provide a start time")
    private LocalTime startTime;

    @JsonProperty("end_time")
    @NotNull(message = "Please provide an end time")
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
    public AvailabilityRequest(
        LocalDate startDate,
        LocalDate endDate,
        LocalTime startTime,
        LocalTime endTime
    ) {
        Objects.requireNonNull(startDate, "start date must not be null");
        Objects.requireNonNull(endDate, "end date must not be null");
        Objects.requireNonNull(startTime, "start time must not be null");
        Objects.requireNonNull(endTime, "end time must not be null");

        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
