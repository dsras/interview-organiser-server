package com.accolite.intervieworganiser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/*
represents an interview and interviewers in a format easily sent in request by front-end
Interview and InterviewInterviewer entries created from this
 */
public class InterviewRequest {

    @JsonProperty("interviewer_ids")
    private List<Integer> interviewerIds;

    @JsonProperty("date")
    private LocalDate date;

    @JsonProperty("start_time")
    private LocalTime startTime;

    @JsonProperty("end_time")
    private LocalTime endTime;

    @JsonProperty("additional_info")
    private String info;

    public List<Integer> getInterviewerIds() {
        return interviewerIds;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getInfo() {
        return info;
    }

}
