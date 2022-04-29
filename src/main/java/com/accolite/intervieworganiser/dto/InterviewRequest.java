package com.accolite.intervieworganiser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Stores information about a new interview.
 * <p>Interview data transfer object that stores date, start time, end time, list of IDs of
 * interviewers on panel and any additional info provided by recruiter.
 * {@link com.accolite.intervieworganiser.entities.Interview} objects are created with this
 * information.</p>
 */
public class InterviewRequest {

    @JsonProperty("interviewer_ids")
    @NotEmpty(message = "Please provide list of interviewer IDs")
    private List<Integer> interviewerIds;

    @JsonProperty("date")
    private LocalDate date;

    @JsonProperty("start_time")
    private LocalTime startTime;

    @JsonProperty("end_time")
    private LocalTime endTime;

    @JsonProperty("additional_info")
    private String info;

    /**
     * Parameterised constructor.
     *
     * @param date the interview date
     * @param startTime the interview start time
     * @param endTime the interview end time
     * @param interviewerIds the list of interviewer IDs
     */
    public InterviewRequest(LocalDate date, LocalTime startTime, LocalTime endTime, List<Integer> interviewerIds){
        this.date=date;
        this.startTime=startTime;
        this.endTime=endTime;
        this.interviewerIds=interviewerIds;
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
