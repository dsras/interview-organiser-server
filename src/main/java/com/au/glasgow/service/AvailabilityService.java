package com.au.glasgow.service;

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

    /* create new availability */
    public AvailabilityRequest save(AvailabilityRequestWrapper newAvailability) {
        User user = newAvailability.getUser();
        LocalDate date = newAvailability.getDate();
        LocalTime startTime = newAvailability.getStartTime();
        LocalTime endTime = newAvailability.getEndTime();

        availabilityRepository.save(new UserAvailability(user, date, startTime, endTime));
        return new AvailabilityRequest(date, startTime, endTime);
    }

    /* get user's availability */
    public List<AvailabilityRequest> get(String username){
        List<AvailabilityRequest> availabilityRequests = new ArrayList<>();
        for (UserAvailability av : availabilityRepository.getByUsername(username)) {
            AvailabilityRequest tempReq = new AvailabilityRequest(av.getAvailableDate(), av.getAvailableFrom(), av.getAvailableTo());
            availabilityRequests.add(tempReq);
        }
        return availabilityRequests;
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

    /* delete all availabilities - for Thorfinn testing */
    public void clear(){
        List<UserAvailability> availabilities = availabilityRepository.findAll();
        for (UserAvailability a : availabilities){
            availabilityRepository.delete(a);
        }
    }


}
