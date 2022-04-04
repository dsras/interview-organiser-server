package com.au.glasgow.requestModels;

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
public class AvailableUsersRequest {

    @JsonProperty("date")
    private LocalDate date;
    @JsonProperty("start_time")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @JsonProperty("end_time")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;
    @JsonProperty("skills")
    private List<Integer> skills;
    public AvailableUsersRequest() {
    }

    /*
    how to generate skills
    list of skill IDs (would need to store IDs in UI)
    get skills by ID from repo
     */
    public AvailableUsersRequest(LocalDate date, LocalTime startTime, LocalTime endTime, List<Integer> skillIds) {
        this.date=date;
        this.startTime=startTime;
        this.endTime=endTime;
        this.skills=skillIds;
    }

    public LocalTime getStartTime(){return startTime;}
    public LocalDate getDate(){return date;}
}