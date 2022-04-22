package com.au.glasgow.service;

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
public class InterviewService {

    @Autowired
    InterviewRepository interviewRepository;

    @Autowired
    InterviewInterviewerRepository interviewInterviewerRepository;

    @Autowired
    UserService userService;

    @Autowired
    AvailabilityService availabilityService;

    /* update interview status */
    public InterviewResponse updateStatus(String status, Integer id) {
        Interview i = interviewRepository.getById(id);
        i.setStatus(status);
        i = interviewRepository.save(i);
        List<User> interviewers = i.getInterviewInterviewers().stream()
                .map(InterviewInterviewer::getInterviewer)
                .collect(Collectors.toList());
        return new InterviewResponse(i, interviewers);
    }

    /* update interview outcome */
    public InterviewResponse updateOutcome(String outcome, Integer id) {
        Interview i = interviewRepository.getById(id);
        i.setOutcome(outcome);
        i = interviewRepository.save(i);
        List<User> interviewers = i.getInterviewInterviewers().stream()
                .map(InterviewInterviewer::getInterviewer)
                .collect(Collectors.toList());
        return new InterviewResponse(i, interviewers);
    }

    /* save Interview and return new InterviewResponse representing new interview */
    public InterviewResponse save(InterviewRequestWrapper wrapper) {
        /* amend availability to reflect new booking */
        availabilityService.amendAvailability(wrapper);

        /* create Interview entry */
        String info = wrapper.getInfo();
        List<User> interviewers = wrapper.getInterviewerIds().stream()
                .map(x -> userService.getById(x))
                .collect(Collectors.toList());
        Interview interview = new Interview(wrapper.getUser(), wrapper.getDate(),
                wrapper.getStartTime(), wrapper.getEndTime(), info);
        interview = interviewRepository.save(interview);

        /* create InterviewInterviewer entries */
        for (User u : interviewers) {
            interviewInterviewerRepository.save(new InterviewInterviewer(interview, u));
        }

        /* return InterviewResponse */
        return new InterviewResponse(interview, interviewers);
    }

    /* find all interviews that interviewer is participating in */
    public List<InterviewResponse> findByInterviewer(User user){
        List<Interview> interviews = interviewRepository.findAllByInterviewer(user);
        return getInterviewResponseList(interviews);
    }

    /* find all interviews that recruiter has organised */
    public List<InterviewResponse> findByRecruiter(User user){
        List<Interview> interviews = interviewRepository.findAllByRecruiter(user);
        return getInterviewResponseList(interviews);
    }

    /* find all interviews */
    public List<InterviewResponse> findAll(){
        List<Interview> interviews = interviewRepository.findAll();
        return getInterviewResponseList(interviews);
    }

    /* create InterviewResponse objects for all Interviews */
    private List<InterviewResponse> getInterviewResponseList(List<Interview> interviews){
        List<InterviewResponse> response = new ArrayList<>();
        for (Interview i : interviews){
            response.add(new InterviewResponse(i, interviewRepository.findInterviewers(i.getId())));
        }
        return response;
    }

    /* find interviews completed by the interviewer */
    public Integer findCompleted(User user){
        return interviewRepository.findCompleted(user);
    }

    /* find interviews with status 'Confirmed' organised by the recruiter */
    public List<InterviewResponse> findConfirmed(User user){
        return getInterviewResponseList(interviewRepository.findConfirmed(user));
    }

    /* find interviews with status 'Candidate No Show' organised by the recruiter */
    public List<InterviewResponse> findCNS(User user){
        return getInterviewResponseList(interviewRepository.findCNS(user));
    }

    /* find interviews with status 'Panel No Show' organised by the recruiter */
    public List<InterviewResponse> findPNS(User user){
        return getInterviewResponseList(interviewRepository.findPNS(user));
    }
}
