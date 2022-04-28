package com.accolite.intervieworganiser.dto;

import com.accolite.intervieworganiser.entities.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class InterviewRequestWrapper {

    private InterviewRequest interviewRequest;
    private User user; //organiser

    public InterviewRequestWrapper(InterviewRequest request, User user){
        this.interviewRequest=request;
        this.user = user;
    }

    /**
     *
     * @return the interview organiser
     */
    public User getUser(){
        return user;
    }

    public List<Integer> getInterviewerIds(){
        return interviewRequest.getInterviewerIds();
    }

    public LocalDate getDate(){
        return interviewRequest.getDate();
    }

    public LocalTime getStartTime(){
        return interviewRequest.getStartTime();
    }

    public LocalTime getEndTime(){
        return interviewRequest.getEndTime();
    }

    public String getInfo(){
        return interviewRequest.getInfo();
    }

}
