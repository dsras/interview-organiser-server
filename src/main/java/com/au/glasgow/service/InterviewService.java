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

    @Autowired
    ApplicantService applicantService;

    @Autowired
    SkillService skillService;

    /* confirm interview has happened and return InterviewResponse representing confirmed interview */
    public InterviewResponse confirm(Integer id) {
        Interview i = interviewRepository.getById(id);
        i.confirm();
        i = interviewRepository.save(i);
        List<User> interviewers = i.getInterviewInterviewers().stream()
                .map(InterviewInterviewer::getInterviewer)
                .toList();
        Skill skill = skillService.getById(i.getApplicant().getSkillId());
        return new InterviewResponse(i, interviewers, skill);
    }

    /* save Interview and return new InterviewResponse representing new interview */
    public InterviewResponse save(InterviewRequestWrapper wrapper) {
        /* amend availability to reflect new booking */
        availabilityService.amendAvailability(wrapper);

        /* update Applicant skill */
        applicantService.updateSkill(wrapper.getApplicantId(), wrapper.getSkillId());

        /* create Interview entry */
        Applicant applicant = applicantService.getById(wrapper.getApplicantId());
        Skill skill = skillService.getById(wrapper.getSkillId());
        List<User> interviewers = wrapper.getInterviewerIds().stream()
                .map(x -> userService.getById(x))
                .toList();
        Interview interview = new Interview(wrapper.getUser(), applicant, wrapper.getDate(),
                wrapper.getStartTime(), wrapper.getEndTime());
        interview = interviewRepository.save(interview);

        /* create InterviewInterviewer entries */
        for (User u : interviewers) {
            interviewInterviewerRepository.save(new InterviewInterviewer(interview, u));
        }

        /* return InterviewResponse */
        return new InterviewResponse(interview, interviewers, skill);
    }

    /* find all interviews that interviewer is participating in */
    public List<InterviewResponse> findByInterviewer(User user){
        List<Interview> interviews = interviewRepository.findAllByInterviewer(user);
        List<InterviewResponse> response = new ArrayList<>();

        /* create InterviewResponse objects for all Interviews */
        for (Interview i : interviews){
            response.add(new InterviewResponse(i, interviewRepository.findInterviewers(i.getId()),
                    interviewRepository.getSkillsByInterview(i.getId())));
        }
        return response;
    }
}
