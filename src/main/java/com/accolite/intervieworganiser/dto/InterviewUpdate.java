package com.accolite.intervieworganiser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InterviewUpdate {

    @JsonProperty("interview_id")
    private Integer interviewId;

    @JsonProperty("update")
    private String update;

    public Integer getInterviewId() {
        return interviewId;
    }

    public String getUpdate() {
        return update;
    }
}
