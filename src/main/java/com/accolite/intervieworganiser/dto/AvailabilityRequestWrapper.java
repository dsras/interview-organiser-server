package com.accolite.intervieworganiser.dto;

import com.accolite.intervieworganiser.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Wraps availability request with interviewer.
 */
public class AvailabilityRequestWrapper {

    private AvailabilityRequest availabilityRequest;
    private User interviewer;

    /**
     * Parameterised constructor.
     *
     * @param request the interviewer's availability request
     * @param interviewer the interviewer
     */
    public AvailabilityRequestWrapper(AvailabilityRequest request, User interviewer){
        this.availabilityRequest=request;
        this.interviewer = interviewer;
    }

    /**
     * Gets the interviewer's ID.
     *
     * @return the interviewer's ID
     */
    public Integer getId(){
        return interviewer.getId();
    }

    /**
     * Gets the interviewer's name.
     *
     * @return the interviewer's name
     */
    public String getName(){
        return interviewer.getName();
    }

    /**
     * Gets the availability request start date.
     *
     * @return the availability request start date
     */
    public LocalDate getStartDate(){
        return availabilityRequest.getStartDate();
    }

    /**
     * Gets the availability request end date.
     *
     * @return the availability request end date
     */
    public LocalDate getEndDate(){
        return availabilityRequest.getEndDate();
    }

    /**
     * Gets the availability request start time.
     *
     * @return the availability request start time
     */
    public LocalTime getStartTime(){
        return availabilityRequest.getStartTime();
    }

    /**
     * Gets the availability request end time.
     *
     * @return the availability request end time
     */
    public LocalTime getEndTime(){
        return availabilityRequest.getEndTime();
    }

    /**
     * Gets the interviewer.
     *
     * @return the interviewer
     */
    @JsonIgnore
    public User getInterviewer(){
        return interviewer;
    }

}
