package com.accolite.intervieworganiser.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Stores information about a request for interviewer(s) with specified availability and skills.
 * <p>Interview/availability data transfer object that stores date and time range during which
 * recruiter would like to create an interview, as well as list of skill IDs required of interviewer(s).</p>
 */
@Getter
@Setter
public class FindInterviewersRequest {

    @JsonProperty("start_date")
    @NotNull(message = "Please provide a start date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate startDate;

    @JsonProperty("end_date")
    @NotNull(message = "Please provide an end date")
    private LocalDate endDate;

    @JsonProperty("start_time")
    @JsonFormat(pattern = "HH:mm")
    @NotNull(message = "Please provide a start time")
    private LocalTime startTime;

    @JsonProperty("end_time")
    @JsonFormat(pattern = "HH:mm")
    @NotNull(message = "Please provide an end time")
    private LocalTime endTime;

    @JsonProperty("skills")
    @NotEmpty(message = "Please provide a list of required skill IDs")
    private List<Integer> skills;

    public FindInterviewersRequest() {}

    /**
     * Parameterised constructor.
     *
     * @param startDate the start date of date range
     * @param endDate the end date of date range
     * @param startTime the start time of time range
     * @param endTime the end time of time range
     * @param skillIds the list of skill IDs indicating skills required by interviewer(s)
     */
    public FindInterviewersRequest(
        LocalDate startDate,
        LocalDate endDate,
        LocalTime startTime,
        LocalTime endTime,
        List<Integer> skillIds
    ) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.skills = skillIds;
    }

    /**
     * Gets start time of time range.
     *
     * @return the start time
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Gets the end time of time range.
     *
     * @return the end time
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Gets the start date of date range.
     *
     * @return the start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Gets the end date of date range.
     *
     * @return the end date
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Gets the list of skill IDs required by interviewer(s).
     *
     * @return the list of skill IDs
     */
    public List<Integer> getSkills() {
        return skills;
    }
}
