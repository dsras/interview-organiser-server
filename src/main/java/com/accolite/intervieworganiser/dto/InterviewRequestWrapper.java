package com.accolite.intervieworganiser.dto;

import com.accolite.intervieworganiser.entities.User;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

/**
 * Wraps {@link InterviewRequest} with interview organiser, a {@link User}.
 */
public class InterviewRequestWrapper {

    private InterviewRequest interviewRequest;
    private User organiser;

    /**
     * Parameterised constructor.
     *
     * @param request the interview request
     * @param organiser the interview organiser
     */
    public InterviewRequestWrapper(InterviewRequest request, User organiser) {
        Objects.requireNonNull(request, "InterviewRequest is required");
        Objects.requireNonNull(organiser, "Organiser is required");
        this.interviewRequest = request;
        this.organiser = organiser;
    }

    /**
     * Gets the interview organiser.
     *
     * @return the interview organiser
     */
    public User getOrganiser() {
        return organiser;
    }

    /**
     * Gets list of IDs of interviewers on interview panel.
     *
     * @return list of interviewer IDs
     */
    public List<Integer> getInterviewerIds() {
        return interviewRequest.getInterviewerIds();
    }

    /**
     * Gets interview date.
     *
     * @return the interview date
     */
    public LocalDate getDate() {
        return interviewRequest.getDate();
    }

    /**
     * Gets interview start time.
     *
     * @return the interview start time
     */
    public LocalTime getStartTime() {
        return interviewRequest.getStartTime();
    }

    /**
     * Gets interview end time.
     *
     * @return the interview end time
     */
    public LocalTime getEndTime() {
        return interviewRequest.getEndTime();
    }

    /**
     * Gets additional information about the interview as provided by the recruiter.
     *
     * @return the additional info
     */
    public String getInfo() {
        return interviewRequest.getInfo();
    }
}
