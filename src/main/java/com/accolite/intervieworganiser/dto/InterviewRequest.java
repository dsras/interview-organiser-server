package com.accolite.intervieworganiser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/*
represents an interview and interviewers in a format easily sent in request by front-end
Interview and InterviewInterviewer entries created from this
 */
public class InterviewRequest {

    @JsonProperty("interviewer_ids")
    @NotEmpty(message = "Please provide list of interviewer IDs")
    private List<Integer> interviewerIds;

    @JsonProperty("date")
    @NotEmpty(message = "Please provide interview date")
    private LocalDate date;

    @JsonProperty("start_time")
    @NotEmpty(message = "Please provide interview start time")
    private LocalTime startTime;

    @JsonProperty("end_time")
    @NotEmpty(message = "Please provide interview end time")
    private LocalTime endTime;

    @JsonProperty("additional_info")
    private String info;

    public InterviewRequest(LocalDate date, LocalTime startTime, LocalTime endTime, List<Integer> interviewerIds){
        this.date=date;
        this.startTime=startTime;
        this.endTime=endTime;
        this.interviewerIds=interviewerIds;
    }

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
