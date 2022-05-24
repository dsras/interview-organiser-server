package com.accolite.intervieworganiser.service;

import com.accolite.intervieworganiser.entities.Interview;
import com.accolite.intervieworganiser.entities.InterviewPanel;
import com.accolite.intervieworganiser.entities.User;
import com.accolite.intervieworganiser.repository.InterviewPanelRepository;
import com.accolite.intervieworganiser.repository.InterviewRepository;
import com.accolite.intervieworganiser.dto.InterviewRequestWrapper;
import com.accolite.intervieworganiser.dto.InterviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InterviewService {

    InterviewRepository interviewRepository;
    InterviewPanelRepository interviewPanelRepository;
    UserService userService;
    AvailabilityService availabilityService;

    /**
     * Parameterised constructor.
     *
     * @param interviewRepository interview data access layer
     * @param interviewPanelRepository interview panelist data access layer
     * @param userService user service layer
     * @param availabilityService availability service layer
     */
    public InterviewService(@Autowired  InterviewRepository interviewRepository,
                            @Autowired InterviewPanelRepository interviewPanelRepository,
                            @Autowired UserService userService,
                            @Autowired AvailabilityService availabilityService){
        this.interviewRepository = interviewRepository;
        this.interviewPanelRepository = interviewPanelRepository;
        this.userService = userService;
        this.availabilityService = availabilityService;
    }

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
        Interview interview = new Interview(wrapper.getOrganiser(), wrapper.getDate(),
                wrapper.getStartTime(), wrapper.getEndTime(), info);
        interview = interviewRepository.save(interview);

        /* create InterviewPanel entries */
        for (User u : interviewers) {
            interviewPanelRepository.save(new InterviewPanel(interview, u));
        }

        /* return InterviewResponse */
        return new InterviewResponse(interview, interviewers);
    }

    /* find all interviews that interviewer is participating in */
    public List<com.accolite.intervieworganiser.dto.Interview> findByInterviewer(User user){
        return interviewRepository.findAllByInterviewer(user.getId(), LocalDate.now().minusDays(28));
    }

    /* find all interviews that recruiter has organised */
    public List<com.accolite.intervieworganiser.dto.Interview> findByRecruiter(User user){
        return interviewRepository.findAllByRecruiter(user.getId(), LocalDate.now().minusDays(28));
    }

    /* find all interviews */
    public List<com.accolite.intervieworganiser.dto.Interview> findAll(){
        return interviewRepository.findAllInterviews();
    }

    /* find interviews completed by the interviewer */
    public Integer findCompleted(User user){
        return interviewRepository.findCompleted(user);
    }

    /* find interviews with specified status organised by specified user */
    public List<com.accolite.intervieworganiser.dto.Interview> findByStatus(User user, String status){
        String statusString;
        switch (status) {
            case "confirmed" -> statusString = "Confirmed";
            case "panel-no-show" -> statusString = "Panel No Show";
            case "candidate-no-show" -> statusString = "Candidate No Show";
            default -> statusString = status;
        }
        return interviewRepository.findByStatus(user.getId(), statusString, LocalDate.now().minusDays(28));
    }

    /* find interviews from last 28 days with specified outcome organised by the specified user */
    public List<com.accolite.intervieworganiser.dto.Interview> findByOutcome(User user, String outcome){
        String outcomeString;
        switch (outcome) {
            case "progressed" -> outcomeString = "Progressed";
            case "not-progressed" -> outcomeString = "Didn't Progress";
            case "hired" -> outcomeString = "Hired";
            default -> outcomeString = outcome;
        }
        return interviewRepository.findByOutcome(user.getId(), LocalDate.now().minusDays(28),
                outcomeString);
    }

}
