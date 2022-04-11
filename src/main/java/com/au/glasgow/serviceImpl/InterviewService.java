package com.au.glasgow.serviceImpl;

import com.au.glasgow.entities.Applicant;
import com.au.glasgow.entities.Interview;
import com.au.glasgow.entities.User;
import com.au.glasgow.repository.InterviewRepository;
import com.au.glasgow.requestModels.InterviewRequestWrapper;
import com.au.glasgow.requestModels.InterviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class InterviewService{

    @Autowired
    InterviewRepository interviewRepository;

    @Autowired
    UserService userService;

    @Autowired
    ApplicantService applicantService;

    public Interview getById(Integer id){ return interviewRepository.getById(id);}

//    public InterviewResponse save(InterviewRequestWrapper newInterview) {
//        User organiser = newInterview.getUser();
//        List<User> interviewers = userService.getByIds(newInterview.getInterviewerIds());
//        Applicant applicant = applicantService.getById(newInterview.getApplicantId());
//        LocalDate date = newInterview.getDate();
//        LocalTime startTime = newInterview.getStartTime();
//        LocalTime endTime = newInterview.getEndTime();
//
//        Interview i = interviewRepository.save(new Interview(organiser, applicant, date, startTime, endTime));
//    }

    public Interview confirm(Integer id){
        Interview i = interviewRepository.getById(id);
        i.confirm();
        return interviewRepository.confirmInterview(id);
    }

//    public Interview save(InterviewRequestWrapper wrapper) {
//    }
}
