package com.au.glasgow.dto;

import com.au.glasgow.entities.Interview;
import com.au.glasgow.entities.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/*
represents an interview in a format easily received by front-end (with no need for retrieval of details based
on user ID etc.)
represents interview by:
- interview ID
- list of names of interviewers
- name of organiser
- name of applicant
- name & level of skill being assessed
- date
- start time
- end time
- boolean indicating if interview has been confirmed to have taken place (0 => not confirmed)
 */
public class InterviewResponse {

    @JsonProperty("interview_id")
    private Integer interviewId;

    @JsonProperty("interviewers")
    private List<String> interviewers;

    @JsonProperty("organiser")
    private String organiser;

    @JsonProperty("date")
    private LocalDate date;

    @JsonProperty("start_time")
    private LocalTime startTime;

    @JsonProperty("end_time")
    private LocalTime endTime;

    @JsonProperty("status")
    private String status;

    @JsonProperty("outcome")
    private String outcome;

    @JsonProperty("additional_info")
    private String info;

    public InterviewResponse(Interview interview, List<User> interviewerList) {
        this.interviewId = interview.getId();
        this.organiser = interview.getOrganiser().getUserName();
        this.date = interview.getInterviewDate();
        this.startTime = interview.getTimeStart();
        this.endTime = interview.getTimeEnd();
        this.status = interview.getStatus();
        this.outcome = interview.getOutcome();
        this.info = interview.getInfo();
        this.interviewers = interviewerList.stream()
                .map(User::getUserName).collect(Collectors.toList());

    }

}
