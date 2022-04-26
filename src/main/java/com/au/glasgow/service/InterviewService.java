package com.au.glasgow.service;

import com.au.glasgow.entities.*;
import com.au.glasgow.repository.InterviewPanelRepository;
import com.au.glasgow.repository.InterviewRepository;
import com.au.glasgow.dto.InterviewRequestWrapper;
import com.au.glasgow.dto.InterviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InterviewService {

    @Autowired
    InterviewRepository interviewRepository;

    @Autowired
    InterviewPanelRepository interviewInterviewerRepository;

    @Autowired
    UserService userService;

    @Autowired
    AvailabilityService availabilityService;

    /* update interview status */
    public InterviewResponse updateStatus(String status, Integer id) {
        Interview i = interviewRepository.getById(id);
        i.setStatus(status);
        i = interviewRepository.save(i);
        List<User> interviewers = i.getInterviewPanel().stream()
                .map(InterviewPanel::getInterviewer)
                .collect(Collectors.toList());
        return new InterviewResponse(i, interviewers);
    }

    /* update interview outcome */
    public InterviewResponse updateOutcome(String outcome, Integer id) {
        Interview i = interviewRepository.getById(id);
        i.setOutcome(outcome);
        i = interviewRepository.save(i);
        List<User> interviewers = i.getInterviewPanel().stream()
                .map(InterviewPanel ::getInterviewer)
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
            interviewInterviewerRepository.save(new InterviewPanel(interview, u));
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
        return getInterviewResponseList(interviewRepository.findStatus(user, "Confirmed"));
    }

    /* find interviews with status 'Candidate No Show' organised by the recruiter */
    public List<InterviewResponse> findCNS(User user){
        return getInterviewResponseList(interviewRepository.findStatus(user, "Candidate No Show"));
    }

    /* find interviews with status 'Panel No Show' organised by the recruiter */
    public List<InterviewResponse> findPNS(User user){
        return getInterviewResponseList(interviewRepository.findStatus(user, "Panel No Show"));
    }

    /* find interviews with outcome 'Progressed' organised by the recruiter */
    public List<InterviewResponse> findProgressed(User user){
        return getInterviewResponseList(interviewRepository.findOutcome
                (user, LocalDate.now().minusDays(28), "Progressed"));
    }

    /* find interviews with outcome 'Didn't Progress' organised by the recruiter */
    public List<InterviewResponse> findNotProgressed(User user){
        return getInterviewResponseList(interviewRepository.findOutcome
                (user, LocalDate.now().minusDays(28), "Didn't Progress"));
    }

    /* find interviews with outcome 'Hired' organised by the recruiter */
    public List<InterviewResponse> findHired(User user){
        return getInterviewResponseList(interviewRepository.findOutcome
                (user, LocalDate.now().minusDays(28), "Hired"));
    }
}
