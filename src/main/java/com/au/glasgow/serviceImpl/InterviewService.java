package com.au.glasgow.serviceImpl;

import com.au.glasgow.entities.*;
import com.au.glasgow.repository.InterviewInterviewerRepository;
import com.au.glasgow.repository.InterviewRepository;
import com.au.glasgow.dto.InterviewRequestWrapper;
import com.au.glasgow.dto.InterviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InterviewService{

    @Autowired
    InterviewRepository interviewRepository;

    @Autowired
    InterviewInterviewerRepository interviewInterviewerRepository;

    @Autowired
    UserService userService;

    @Autowired
    ApplicantService applicantService;

    @Autowired
    SkillService skillService;

    public Interview getById(Integer id){
        return interviewRepository.getById(id);
    }

    /* confirm interview has happened and return InterviewResponse representing confirmed interview */
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

    /* save Interview and return new InterviewResponse representing new interview */
    public InterviewResponse save(InterviewRequestWrapper wrapper) {
        System.err.println(wrapper.getInterviewerIds());
        Applicant applicant = applicantService.getById(wrapper.getApplicantId());
        Skill skill = skillService.getById(wrapper.getSkillId());
        List<User> interviewers = wrapper.getInterviewerIds().stream()
                .map(x -> userService.getById(x))
                .collect(Collectors.toList());
        Interview interview = new Interview(wrapper.getUser(), applicant, wrapper.getDate(),
                wrapper.getStartTime(), wrapper.getStartTime());
        interview = interviewRepository.save(interview);
        for (User u : interviewers) {
            interviewInterviewerRepository.save(new InterviewInterviewer(interview, u));
        }
        return new InterviewResponse(interview,  interviewers, skill);
    }

    /* find all interviews that interviewer is participating in
    return list of InterviewResponse objects representing these interviews */
    public List<InterviewResponse> findByInterviewer(User user){
        List<Interview> interviews = interviewRepository.findAllByInterviewer(user);
        List<InterviewResponse> response = new ArrayList<>();
        for (Interview i : interviews){
            response.add(new InterviewResponse(i, interviewRepository.findInterviewers(i.getId()),
                    interviewRepository.getSkillsByInterview(i.getId())));
        }
        return response;
    }
}
