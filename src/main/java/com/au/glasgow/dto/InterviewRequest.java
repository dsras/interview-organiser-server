package com.au.glasgow.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class InterviewRequest {

    @JsonProperty("interviewer_ids")
    private List<Integer> interviewerIds;

    @JsonProperty("applicant_id")
    private Integer applicantId;

    @JsonProperty("skill_id")
    private Integer skillId;

    @JsonProperty("date")
    private LocalDate date;

    @JsonProperty("start_time")
    private LocalTime startTime;

    @JsonProperty("end_time")
    private LocalTime endTime;

    /* getters */
    public List<Integer> getInterviewerIds() {
        return interviewerIds;
    }

    public Integer getApplicantId() {
        return applicantId;
    }

    public Integer getSkillId() {
        return skillId;
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

}
