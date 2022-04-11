package com.au.glasgow.serviceImpl;

import com.au.glasgow.entities.Applicant;
import com.au.glasgow.entities.Interview;
import com.au.glasgow.entities.Skill;
import com.au.glasgow.entities.User;
import com.au.glasgow.repository.InterviewRepository;
import com.au.glasgow.requestModels.InterviewRequestWrapper;
import com.au.glasgow.requestModels.InterviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InterviewService{

    @Autowired
    InterviewRepository interviewRepository;

    @Autowired
    UserService userService;

    @Autowired
    ApplicantService applicantService;

    @Autowired
    SkillService skillService;

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

    public InterviewResponse confirm(Integer id){
        Interview i = interviewRepository.getById(id);
        i.confirm();
        i = interviewRepository.save(i);
        List<User> interviewers = i.getInterviewInterviewers().stream()
                .map(x -> x.getInterviewer())
                .collect(Collectors.toList());
        Skill skill = skillService.getById(i.getApplicant().getSkillId());
        return new InterviewResponse(i, interviewers, skill);
    }

    /* save Interview and return new InterviewResponse*/
    public InterviewResponse save(InterviewRequestWrapper wrapper) {
        Applicant applicant = applicantService.getById(wrapper.getApplicantId());
        Skill skill = skillService.getById(wrapper.getSkillId());
        List<User> interviewers = wrapper.getInterviewerIds().stream()
                .map(i -> userService.getById(i))
                .collect(Collectors.toList());
        Interview interview = new Interview(wrapper.getUser(), applicant, wrapper.getDate(),
                wrapper.getStartTime(), wrapper.getStartTime());
        return new InterviewResponse(interview,  interviewers, skill);
    }
}
