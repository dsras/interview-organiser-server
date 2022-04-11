package com.au.glasgow.requestModels;

import com.au.glasgow.entities.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class InterviewRequestWrapper {

    private InterviewRequest interviewRequest;
    private User user;

    public InterviewRequestWrapper(InterviewRequest request, User user){
        this.interviewRequest=request;
        this.user = user;
    }

    public User getUser(){
        return user;
    }

    public List<Integer> getInterviewerIds() { return interviewRequest.getInterviewerIds();}

    public Integer getApplicantId() { return interviewRequest.getApplicantId();}

    public Integer getSkillId() { return interviewRequest.getSkillId();}

    public LocalDate getDate(){
        return interviewRequest.getDate();
    }

    public LocalTime getStartTime(){
        return interviewRequest.getStartTime();
    }

    public LocalTime getEndTime(){
        return interviewRequest.getEndTime();
    }
}
