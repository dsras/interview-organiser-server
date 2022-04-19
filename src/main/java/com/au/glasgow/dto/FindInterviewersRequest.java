package com.au.glasgow.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


/*
request model
to accept request for available interviewers
 */
@Getter
@Setter
public class FindInterviewersRequest {

    @JsonProperty("start_date")
    private LocalDate startDate;
    @JsonProperty("end_date")
    private LocalDate endDate;
    @JsonProperty("start_time")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @JsonProperty("end_time")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;
    @JsonProperty("skills")
    private List<Integer> skills;
    public FindInterviewersRequest() {}
    public FindInterviewersRequest(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, List<Integer> skillIds) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime=startTime;
        this.endTime=endTime;
        this.skills=skillIds;
    }

    public LocalTime getStartTime(){return startTime;}
    public LocalTime getEndTime(){ return endTime;}
    public LocalDate getStartDate(){return startDate;}
    public LocalDate getEndDate(){return endDate;}
    public List<Integer> getSkills(){return skills;}
}