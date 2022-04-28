package com.accolite.intervieworganiser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class InterviewUpdate {

    @JsonProperty("interview_id")
    @NotEmpty(message = "Please provide interview ID")
    private Integer interviewId;

    @JsonProperty("update")
    @NotEmpty(message = "Please provide start time")
    private String update;

    public Integer getInterviewId() {
        return interviewId;
    }

    public String getUpdate() {
        return update;
    }
}
