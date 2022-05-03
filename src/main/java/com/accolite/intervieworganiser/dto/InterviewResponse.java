package com.accolite.intervieworganiser.dto;

import com.accolite.intervieworganiser.entities.User;
import com.accolite.intervieworganiser.entities.Interview;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Wrapper for {@link Interview} object. Provides {@link com.accolite.intervieworganiser.entities.InterviewPanel}
 * information.
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

    /**
     * Parameterised constructor.
     *
     * @param interview the interviewer
     * @param interviewerList list of interviewers on interview
     */
    public InterviewResponse(Interview interview, List<User> interviewerList) {
        this.interviewId = interview.getId();
        this.organiser = interview.getOrganiser().getName();
        this.date = interview.getInterviewDate();
        this.startTime = interview.getTimeStart();
        this.endTime = interview.getTimeEnd();
        this.status = interview.getStatus();
        this.outcome = interview.getOutcome();
        this.info = interview.getInfo();
        this.interviewers = interviewerList.stream()
                .map(User::getName).collect(Collectors.toList());

    }

}
