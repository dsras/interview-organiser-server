package com.accolite.intervieworganiser.dto;

import com.accolite.intervieworganiser.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.time.LocalTime;

/*
wraps availability request to provide access to interviewer also
 */
public class AvailabilityRequestWrapper {

    private AvailabilityRequest availabilityRequest;
    private User user;

    public AvailabilityRequestWrapper(AvailabilityRequest request, User user){
        this.availabilityRequest=request;
        this.user = user;
    }

    public Integer getId(){
        return user.getId();
    }

    public String getName(){
        return user.getName();
    }

    public LocalDate getStartDate(){
        return availabilityRequest.getStartDate();
    }

    public LocalDate getEndDate(){
        return availabilityRequest.getEndDate();
    }

    public LocalTime getStartTime(){
        return availabilityRequest.getStartTime();
    }

    public LocalTime getEndTime(){
        return availabilityRequest.getEndTime();
    }

    @JsonIgnore
    public User getUser(){
        return user;
    }

}
