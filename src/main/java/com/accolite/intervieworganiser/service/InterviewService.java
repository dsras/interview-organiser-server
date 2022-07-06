package com.accolite.intervieworganiser.service;

import com.accolite.intervieworganiser.dto.*;
import com.accolite.intervieworganiser.entities.Interview;
import com.accolite.intervieworganiser.entities.InterviewPanel;
import com.accolite.intervieworganiser.entities.User;
import com.accolite.intervieworganiser.repository.InterviewPanelRepository;
import com.accolite.intervieworganiser.repository.InterviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
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
    public InterviewService(
            @Autowired InterviewRepository interviewRepository,
            @Autowired InterviewPanelRepository interviewPanelRepository,
            @Autowired UserService userService,
            @Autowired AvailabilityService availabilityService
    ) {
        this.interviewRepository = interviewRepository;
        this.interviewPanelRepository = interviewPanelRepository;
        this.userService = userService;
        this.availabilityService = availabilityService;
    }

    public List<AvailTempReturn> deleteInterviewRecompAvail(String id) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        List<AvailTempReturn> avails = this.interviewRepository.getAvailabilitiesToRecompile(id);
        System.out.println(id);

        if (avails.size() == 1) {
            LocalTime startTime1 = avails.get(0).getStart();
            LocalTime IStartTime = avails.get(0).getInterview_start();
            LocalTime myStartTime;
            if (startTime1.toSecondOfDay() < IStartTime.toSecondOfDay()) {
                myStartTime = startTime1;
            } else {
                myStartTime = IStartTime;
            }

            LocalTime endTime1 = avails.get(0).getEnd();
            LocalTime IEndTime = avails.get(0).getInterview_end();
            LocalTime myEndTime;
            if (endTime1.toSecondOfDay() > IEndTime.toSecondOfDay()) {
                myEndTime = endTime1;
            } else {
                myEndTime = IEndTime;
            }

            AvailabilityRequest myAvailRequest = new AvailabilityRequest(
                    avails.get(0).getDate(),
                    avails.get(0).getDate(),
                    myStartTime,
                    myEndTime
            );
            AvailabilityWrapper myWrap = new AvailabilityWrapper(
                    myAvailRequest,
                    userService.findOne(avails.get(0).getUsername())
            );
            this.availabilityService.save(myWrap);
            this.availabilityService.delete(Integer.parseInt(avails.get(0).getAvailability_id()));
            this.interviewRepository.deleteIPanel(Integer.parseInt(id));
            this.interviewRepository.deleteInterview(Integer.parseInt(id));

        } else if (avails.size() == 2) {
            LocalTime startTime1 = avails.get(0).getStart();
            LocalTime startTime2 = avails.get(1).getStart();
            LocalTime IStartTime = avails.get(1).getInterview_start();
            LocalTime myStartTime;
            if (
                startTime1.toSecondOfDay() < IStartTime.toSecondOfDay()
                    && startTime1.toSecondOfDay() < startTime2.toSecondOfDay()
            ) {
                myStartTime = startTime1;
            } else if (IStartTime.toSecondOfDay() < startTime2.toSecondOfDay()) {
                myStartTime = IStartTime;
            } else {
                myStartTime = startTime2;
            }
            LocalTime endTime1 = avails.get(0).getEnd();
            LocalTime endTime2 = avails.get(1).getEnd();
            LocalTime IEndTime = avails.get(1).getInterview_end();
            LocalTime myEndTime;
            if (
                endTime2.toSecondOfDay() > IEndTime.toSecondOfDay()
                    && endTime2.toSecondOfDay() > endTime1.toSecondOfDay()
            ) {
                myEndTime = endTime2;
            } else if (IEndTime.toSecondOfDay() > endTime1.toSecondOfDay()) {
                myEndTime = IEndTime;
            } else {
                myEndTime = endTime1;
            }

            AvailabilityRequest myAvailRequest = new AvailabilityRequest(
                    avails.get(0).getDate(),
                    avails.get(0).getDate(),
                    myStartTime,
                    myEndTime
            );
            AvailabilityWrapper myWrap = new AvailabilityWrapper(
                    myAvailRequest,
                    userService.findOne(avails.get(0).getUsername())
            );
            this.availabilityService.save(myWrap);
            this.availabilityService.delete(Integer.parseInt(avails.get(0).getAvailability_id()));
            this.availabilityService.delete(Integer.parseInt(avails.get(1).getAvailability_id()));
            this.interviewRepository.deleteIPanel(Integer.parseInt(id));
            this.interviewRepository.deleteInterview(Integer.parseInt(id));
        }
        return avails;

    }

    public List<com.accolite.intervieworganiser.dto.Interview> getInterviewsInRange(
            String userName,
            LocalDate startTime,
            LocalDate endTime
    ) {
        return this.interviewRepository.findByInterviewerPerMonth(userName, startTime, endTime);
    }

    /* update interview status */
    public InterviewResponse updateStatus(String status, Integer id) {
        Interview i = interviewRepository.getById(id);
        i.setStatus(status);
        i = interviewRepository.save(i);
        List<User> interviewers = i.getInterviewPanel()
            .stream()
            .map(InterviewPanel::getInterviewer)
            .collect(Collectors.toList());
        return new InterviewResponse(i, interviewers);
    }

    /* update interview outcome */
    public InterviewResponse updateOutcome(String outcome, Integer id) {
        Interview i = interviewRepository.getById(id);
        i.setOutcome(outcome);
        i = interviewRepository.save(i);
        List<User> interviewers = i.getInterviewPanel()
            .stream()
            .map(InterviewPanel::getInterviewer)
            .collect(Collectors.toList());
        return new InterviewResponse(i, interviewers);
    }

    /* save Interview and return new InterviewResponse representing new interview */
    public InterviewResponse save(InterviewRequestWrapper wrapper) {
        /* amend availability to reflect new booking */
        availabilityService.amendAvailability(wrapper);

        /* create Interview entry */
        String info = wrapper.getInfo();
        List<User> interviewers = wrapper.getInterviewerIds()
            .stream()
            .map(x -> userService.getById(x))
            .collect(Collectors.toList());
        Interview interview = new Interview(
                wrapper.getOrganiser(),
                wrapper.getDate(),
                wrapper.getStartTime(),
                wrapper.getEndTime(),
                info,
                wrapper.getStatus(),
                wrapper.getOutcome()
        );
        interview = interviewRepository.save(interview);

        /* create InterviewPanel entries */
        for (User u : interviewers) {
            interviewPanelRepository.save(new InterviewPanel(interview, u));
        }

        /* return InterviewResponse */
        return new InterviewResponse(interview, interviewers);
    }

    /* find all interviews that interviewer is participating in */
    public List<com.accolite.intervieworganiser.dto.Interview> findByInterviewer(User user) {
        return interviewRepository.findAllByInterviewer(user.getId(), LocalDate.now().minusDays(28));
    }

    /* find all interviews that recruiter has organised */
    public List<com.accolite.intervieworganiser.dto.Interview> findByRecruiter(User user) {
        List<com.accolite.intervieworganiser.dto.Interview> myList = interviewRepository.findAllByRecruiter(
            user.getId(),
            LocalDate.now().minusMonths(2)
        );
        return myList;
    }

    /* find all interviews */
    public List<com.accolite.intervieworganiser.dto.Interview> findAll() {
        return interviewRepository.findAllInterviews();
    }

    /* find interviews completed by the interviewer */
    public Integer findCompleted(User user) {
        return interviewRepository.findCompleted(user);
    }

    /* find interviews with specified status organised by specified user */
    public List<com.accolite.intervieworganiser.dto.Interview> findByStatus(User user, String status) {
        String statusString;
        switch (status) {
            case "confirmed":
                statusString = "Confirmed";
                break;
            case "panel-no-show":
                statusString = "Panel No Show";
                break;
            case "candidate-no-show":
                statusString = "Candidate No Show";
                break;
            default:
                statusString = status;
                break;
        }
        return interviewRepository.findByStatus(user.getId(), statusString, LocalDate.now().minusDays(28));
    }

    /* find interviews from last 28 days with specified outcome organised by the specified user */
    public List<com.accolite.intervieworganiser.dto.Interview> findByOutcome(User user, String outcome) {
        String outcomeString;
        switch (outcome) {
            case "progressed":
                outcomeString = "Progressed";
                break;
            case "not-progressed":
                outcomeString = "Didn't Progress";
                break;
            case "hired":
                outcomeString = "Hired";
                break;
            default:
                outcomeString = outcome;
                break;
        }
        return interviewRepository.findByOutcome(
            user.getId(),
            LocalDate.now().minusDays(28),
            outcomeString
        );
    }

}
