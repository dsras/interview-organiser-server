package com.accolite.intervieworganiser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Stores information about a new interview.
 * <p>
 * Interview data transfer object that stores date, start time, end time, list of IDs of
 * interviewers on panel and any additional info provided by recruiter.
 * {@link com.accolite.intervieworganiser.entities.Interview} objects are created with this
 * information.
 * </p>
 */
public class InterviewRequest {

    @JsonProperty("interviewerIds")
    @NotEmpty(message = "Please provide list of interviewer IDs")
    private List<Integer> interviewerIds;

    @JsonProperty("date")
    @NotNull(message = "Please provide an interview date")
    private LocalDate date;

    @JsonProperty("startTime")
    @NotNull(message = "Please provide an interview start time")
    private LocalTime startTime;

    @JsonProperty("endTime")
    @NotNull(message = "Please provide an interview end time")
    private LocalTime endTime;

    @JsonProperty("additionalInfo")
    private String info;

    @JsonProperty("status")
    @NotNull(message = "Please select status")
    private String status;

    @JsonProperty("outcome")
    @NotNull(message = "Please select outcome")
    private String outcome;

    /**
     * Parameterised constructor.
     *
     * @param date the interview date
     * @param startTime the interview start time
     * @param endTime the interview end time
     * @param interviewerIds the list of interviewer IDs
     */
    public InterviewRequest(
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime,
            List<Integer> interviewerIds,
            String status,
            String outcome
    ) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.interviewerIds = interviewerIds;
        this.status = status;
        this.outcome = outcome;
    }

    public String getOutcome() {
        return outcome;
    }

    public String getStatus() {
        return status;
    }

    /**
     * Gets list of IDs of interviewers on interview panel.
     *
     * @return the list of interviewer IDs
     */
    public List<Integer> getInterviewerIds() {
        return interviewerIds;
    }

    /**
     * Gets interview date.
     *
     * @return the interview date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Gets interview start time.
     *
     * @return the interview start time.
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Gets interview end time.
     *
     * @return the interview end time
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Gets interview additional information.
     *
     * @return the additional information
     */
    public String getInfo() {
        return info;
    }
}
