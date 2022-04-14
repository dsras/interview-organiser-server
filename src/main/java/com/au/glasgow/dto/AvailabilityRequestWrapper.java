package com.au.glasgow.dto;

import com.au.glasgow.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.criteria.CriteriaBuilder;
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
        return user.getUserName();
    }

    public LocalDate getDate(){
        return availabilityRequest.getDate();
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
