package com.au.glasgow.requestModels;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class InterviewResponse {

    @JsonProperty("interview_id")
    private Integer interviewId;

    @JsonProperty("interviewers")
    private List<String> interviewers;

    @JsonProperty("organiser")
    private String organiser;

    @JsonProperty("applicant")
    private String applicant;

    @JsonProperty("skill")
    private String skill;

    @JsonProperty("date")
    private LocalDate date;

    @JsonProperty("start_time")
    private LocalTime startTime;

    @JsonProperty("end_time")
    private LocalTime endTime;

    @JsonProperty("confirmed")
    private Integer confirmed;

    /* setters */

    public void setInterviewId(Integer interviewId) {
        this.interviewId = interviewId;
    }

    public void setInterviewers(List<String> interviewers) {
        this.interviewers = interviewers;
    }

    public void setOrganiser(String organiser) {
        this.organiser = organiser;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setConfirmed(Integer confirmed) {
        this.confirmed = confirmed;
    }
}
