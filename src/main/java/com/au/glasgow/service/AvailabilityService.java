package com.au.glasgow.service;

import com.au.glasgow.dto.FindInterviewersRequest;
import com.au.glasgow.dto.InterviewRequestWrapper;
import com.au.glasgow.entities.User;
import com.au.glasgow.entities.UserAvailability;
import com.au.glasgow.repository.AvailabilityRepository;
import com.au.glasgow.dto.AvailabilityRequest;
import com.au.glasgow.dto.AvailabilityRequestWrapper;
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
    public void amendAvailability(InterviewRequestWrapper wrapper) {
        List<UserAvailability> currentAvailability = availabilityRepository.getInTimeInterval(wrapper.getDate(),
                wrapper.getStartTime(), wrapper.getEndTime());
        for (UserAvailability a : currentAvailability) {
            /* if availability perfectly matches interview times: */
            if (ChronoUnit.MINUTES.between(wrapper.getStartTime(), wrapper.getEndTime())
                    == ChronoUnit.MINUTES.between(a.getAvailableFrom(), a.getAvailableTo())){
                /* delete availability */
                availabilityRepository.delete(a);
            }/* if available before interview: */
            else if (a.getAvailableFrom().isBefore(wrapper.getStartTime())) {
                /* and available after: */
                if (a.getAvailableTo().isAfter(wrapper.getEndTime())) {
                    /* create new availability from interview end time until availability end time */
                    availabilityRepository.save(new UserAvailability(a.getUser(), a.getAvailableDate(),
                            wrapper.getEndTime(), a.getAvailableTo()));
                }
                /* update existing availability to end at interview start time */
                a.setAvailableTo(wrapper.getStartTime());
                availabilityRepository.save(a);
            } else { /* if not available before interview but available after: */
                if (a.getAvailableTo().isAfter(wrapper.getEndTime())){
                    /* update existing availability to start at interview end time */
                    a.setAvailableFrom(wrapper.getEndTime());
                    availabilityRepository.save(a);
                }
            }
        }
    }

    /* get availability by skill */
    public List<UserAvailability> findBySkills(List<Integer> skillIds){

        /* get users with all listed skills */
        List<User> users = userSkillService.findBySkills(skillIds, skillIds.size());

        /* get availability of users found to have all skills */
        List<UserAvailability> availabilities = availabilityRepository.getByUsers(users);
        for (UserAvailability a : availabilities){
            a.setInterviewer();
        }
        return availabilities;
    }

    /* get interviewers available in time slot from list of qualified interviewers */
    public List<UserAvailability> getAvailableInterviewers(List<User> users, FindInterviewersRequest request){;
        List<UserAvailability> availabilities = availabilityRepository.getAvailableInterviewers(users,
                request.getStartDate(), request.getEndDate(), request.getStartTime(), request.getEndTime());
        return availabilities;
    }

    /* delete all availabilities - for Thorfinn testing */
    public void clear(){
        List<UserAvailability> availabilities = availabilityRepository.findAll();
        for (UserAvailability a : availabilities){
            availabilityRepository.delete(a);
        }
    }
}
