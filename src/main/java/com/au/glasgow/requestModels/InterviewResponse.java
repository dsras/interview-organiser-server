package com.au.glasgow.requestModels;

import com.au.glasgow.entities.Applicant;
import com.au.glasgow.entities.Interview;
import com.au.glasgow.entities.Skill;
import com.au.glasgow.entities.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public InterviewResponse(Interview interview, List<User> interviewerList, Skill skill) {
        this.interviewId = interview.getId();
        this.organiser = interview.getOrganiser().getUserName();
        this.applicant = interview.getApplicant().getName();
        this.skill = skill.getSkillName()+" "+skill.getSkillLevel();
        this.date = interview.getInterviewDate();
        this.startTime = interview.getTimeStart();
        this.endTime = interview.getTimeEnd();
        this.confirmed = interview.getConfirmed();
        this.interviewers = interviewerList.stream()
                .map(x -> x.getUserName())
                .collect(Collectors.toList());
    }


}
