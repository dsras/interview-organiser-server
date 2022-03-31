package com.au.glasgow.requestModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class AvailableUsersRequest {

    @JsonProperty("date")
    private LocalDate date;
    @JsonProperty("start_time")
    private LocalTime startTime;
    @JsonProperty("end_time")
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
}