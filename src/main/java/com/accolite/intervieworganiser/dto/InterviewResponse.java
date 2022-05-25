package com.accolite.intervieworganiser.dto;

import com.accolite.intervieworganiser.entities.Interview;
import com.accolite.intervieworganiser.entities.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

/**
 * Wrapper for {@link Interview} object. Provides {@link com.accolite.intervieworganiser.entities.InterviewPanel}
 * information.
 */
public class InterviewResponse {

    @JsonProperty("interviewId")
    private Integer interviewId;

    @JsonProperty("interviewers")
    private List<String> interviewers;

    @JsonProperty("organiser")
    private String organiser;

    @JsonProperty("date")
    private LocalDate date;

    @JsonProperty("startTime")
    private LocalTime startTime;

    @JsonProperty("endTime")
    private LocalTime endTime;

    @JsonProperty("status")
    private String status;

    @JsonProperty("outcome")
    private String outcome;

    @JsonProperty("additionalInfo")
    private String info;

    public Integer getInterviewId() {
        return interviewId;
    }

    public List<String> getInterviewers() {
        return interviewers;
    }

    public String getOrganiser() {
        return organiser;
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

    public String getStatus() {
        return status;
    }

    public String getOutcome() {
        return outcome;
    }

    public String getInfo() {
        return info;
    }

    /**
     * Parameterised constructor.
     *
     * @param interview the interviewer
     * @param interviewerList list of interviewers on interview
     */
    public InterviewResponse(Interview interview, List<User> interviewerList) {
        Objects.requireNonNull(interview, "interview must not be null");
        Objects.requireNonNull(
            interview.getOrganiser(),
            "organiser must not be null"
        );
        Objects.requireNonNull(
            interview.getInterviewDate(),
            "date must not be null"
        );
        Objects.requireNonNull(
            interview.getTimeStart(),
            "start time must not be null"
        );
        Objects.requireNonNull(
            interview.getTimeEnd(),
            "end time must not be null"
        );

        this.interviewId = interview.getId();
        this.organiser = interview.getOrganiser().getName();
        this.date = interview.getInterviewDate();
        this.startTime = interview.getTimeStart();
        this.endTime = interview.getTimeEnd();
        this.status = interview.getStatus();
        this.outcome = interview.getOutcome();
        this.info = interview.getInfo();
        this.interviewers =
            interviewerList
                .stream()
                .map(User::getName)
                .collect(Collectors.toList());
    }
}
