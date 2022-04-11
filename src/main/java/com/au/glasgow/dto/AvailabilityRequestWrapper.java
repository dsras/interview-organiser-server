package com.au.glasgow.dto;

import com.au.glasgow.entities.User;

import java.time.LocalDate;
import java.time.LocalTime;

public class AvailabilityRequestWrapper {
    private AvailabilityRequest availabilityRequest;
    private User user;

    public AvailabilityRequestWrapper(AvailabilityRequest request, User user){
        this.availabilityRequest=request;
        this.user = user;
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

    public User getUser(){
        return user;
    }

}
