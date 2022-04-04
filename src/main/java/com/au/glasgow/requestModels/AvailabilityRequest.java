package com.au.glasgow.requestModels;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AvailabilityRequest {

    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("date")
    private LocalDate date;
    @JsonProperty("start_time")
    private LocalTime availableFrom;
    @JsonProperty("end_time")
    private LocalTime availableTo;

    public AvailabilityRequest() {
    }

    /*
    how to generate skills
    list of skill IDs (would need to store IDs in UI)
    get skills by ID from repo
     */
    public AvailabilityRequest(LocalDate date, LocalTime startTime, LocalTime endTime, List<Integer> skillIds) {
    }

    public int getUserId(){
        return userId;
    }
}
