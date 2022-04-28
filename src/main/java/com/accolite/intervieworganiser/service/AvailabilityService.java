package com.accolite.intervieworganiser.service;

import com.accolite.intervieworganiser.dto.FindInterviewersRequest;
import com.accolite.intervieworganiser.entities.User;
import com.accolite.intervieworganiser.log.LoggingServiceImpl;
import com.accolite.intervieworganiser.repository.AvailabilityRepository;
import com.accolite.intervieworganiser.dto.InterviewRequestWrapper;
import com.accolite.intervieworganiser.entities.UserAvailability;
import com.accolite.intervieworganiser.dto.AvailabilityRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class AvailabilityService{

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private SkillService skillService;

    @Autowired
    private UserSkillService userSkillService;

    Logger logger = LoggerFactory.getLogger(LoggingServiceImpl.class);

    public AvailabilityService(AvailabilityRepository availabilityRepository, SkillService skillService,
                               UserSkillService userSkillService){
        this.availabilityRepository=availabilityRepository;
        this.skillService=skillService;
        this.userSkillService=userSkillService;
    }

    /* create new availability */
    public List<UserAvailability> save(AvailabilityRequestWrapper newAvailability) {
        List<UserAvailability> newAvailabilities = new ArrayList<>();
        User user = newAvailability.getUser();
        LocalDate startDate = newAvailability.getStartDate();
        LocalDate endDate = newAvailability.getEndDate();
        LocalTime startTime = newAvailability.getStartTime();
        LocalTime endTime = newAvailability.getEndTime();

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            newAvailabilities.add(availabilityRepository.save
                    (new UserAvailability(user, date, startTime, endTime)));
        }
        for (UserAvailability a : newAvailabilities){
            a.setInterviewer();
        }
        return newAvailabilities;
    }

    /* get user's availability */
    public List<UserAvailability> getUserAvailability(String username){
        List<UserAvailability> availability = availabilityRepository.getByUsername(username);
        for (UserAvailability a : availability){
            a.setInterviewer();
        }
        return availability;
    }

    /* get all availability */
    public List<UserAvailability> getAllAvailability(){
        List<UserAvailability> availability = availabilityRepository.findAll();
        for (UserAvailability a : availability){
            a.setInterviewer();
        }
        return availability;
    }

    /* amend availability of user to reflect new booking */
    public List<UserAvailability> amendAvailability(InterviewRequestWrapper wrapper) {
        List<UserAvailability> currentAvailability = availabilityRepository.getInTimeInterval(wrapper.getDate(),
                wrapper.getStartTime(), wrapper.getEndTime());
        /* lists to store availabilities that need to be removed or saved to database */
        List<UserAvailability> availabilitiesToRemove = new ArrayList<>();
        List<UserAvailability> availabilitiesToAdd = new ArrayList<>();
        for (UserAvailability availability : currentAvailability) {
            /* if interview spans entire availability: */
            if (!wrapper.getStartTime().isAfter(availability.getAvailableFrom())
            && !wrapper.getEndTime().isBefore(availability.getAvailableTo())){
                /* delete availability */
                availabilitiesToRemove.add(availability);
            }/* if available before interview: */
            else if (availability.getAvailableFrom().isBefore(wrapper.getStartTime())) {
                /* and available after: */
                if (availability.getAvailableTo().isAfter(wrapper.getEndTime())) {
                    /* create new availability from interview end time until availability end time */
                    UserAvailability newAvailability = new UserAvailability(availability.getUser(),
                            availability.getAvailableDate(), wrapper.getEndTime(), availability.getAvailableTo());
                    availabilitiesToAdd.add(newAvailability);
                }
                /* update existing availability to end at interview start time */
                availability.setAvailableTo(wrapper.getStartTime());
                availabilitiesToAdd.add(availability);
            } else { /* if not available before interview but available after: */
                if (availability.getAvailableTo().isAfter(wrapper.getEndTime())){
                    /* update existing availability to start at interview end time */
                    availability.setAvailableFrom(wrapper.getEndTime());
                    availabilitiesToAdd.add(availability);
                }
            }
        }
        /* update database with amended availabilities */
        availabilityRepository.deleteAllInBatch(availabilitiesToRemove);
        availabilityRepository.saveAll(availabilitiesToAdd);
        /* return list of new/amended availabilities (for testing) */
        return availabilitiesToAdd;
    }

    /* get availability by skill */
    public List<UserAvailability> findBySkills(List<Integer> skillIds){
        /* get users with all listed skills */
        List<User> users = userSkillService.findBySkills(skillIds, skillIds.size());
        /* get availability of users found to have all skills */
        List<UserAvailability> availabilities = availabilityRepository.getByUsers(users);
        /* assign interviewer attributes to availability objects */
        for (UserAvailability a : availabilities) {
            a.setInterviewer();
        }
        return availabilities;
    }

    /* get interviewers available in time slot from list of qualified interviewers */
    public List<UserAvailability> getAvailableInterviewers(List<User> users, FindInterviewersRequest request){
        /* get available interviewers */
        List<UserAvailability> availabilities = availabilityRepository.getAvailableInterviewers(users,
                request.getStartDate(), request.getEndDate(), request.getStartTime(), request.getEndTime());
        /* assign interviewer attributes to availabilities */
        for (UserAvailability a : availabilities){
            a.setInterviewer();
        }
        return availabilities;
    }
}
