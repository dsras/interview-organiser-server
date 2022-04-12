package com.au.glasgow.dto;

import com.au.glasgow.entities.User;
import com.au.glasgow.entities.UserAvailability;

import java.time.LocalDate;
import java.time.LocalTime;

public class AvailabilityWrapper {

    private User interviewer;
    private UserAvailability availability;

    public AvailabilityWrapper(User interviewer, UserAvailability availability){
        this.availability=availability;
    }

    public String getInterviewer(){
        return interviewer.getUserName();
    }

    public LocalDate getDate(){
        return availability.getAvailableDate();
    }

    public LocalTime getStartTime(){
        return availability.getAvailableFrom();
    }

    public LocalTime getEndTime(){
        return availability.getAvailableTo();
    }
}
