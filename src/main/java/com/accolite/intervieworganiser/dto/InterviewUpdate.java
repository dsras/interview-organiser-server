package com.accolite.intervieworganiser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotEmpty;

/**
 * Stores information about new {@link com.accolite.intervieworganiser.entities.Interview} status or outcome.
 * <p>Interview data transfer object that stores interview ID and new status or outcome. </p>
 */
public class InterviewUpdate {

    @JsonProperty("interview_id")
    @NotEmpty(message = "Please provide interview ID")
    private Integer interviewId;

    @JsonProperty("update")
    @NotEmpty(message = "Please provide start time")
    private String update;

    /**
     * Gets interview ID
     *
     * @return the interview ID
     */
    public Integer getInterviewId() {
        return interviewId;
    }

    /**
     * Gets interview status or outcome update
     *
     * @return the updated status/outcome
     */
    public String getUpdate() {
        return update;
    }
}
