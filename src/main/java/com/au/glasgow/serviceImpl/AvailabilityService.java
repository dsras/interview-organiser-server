package com.au.glasgow.serviceImpl;

import com.au.glasgow.entities.User;
import com.au.glasgow.entities.UserAvailability;
import com.au.glasgow.repository.AvailabilityRepository;
import com.au.glasgow.dto.AvailabilityRequest;
import com.au.glasgow.dto.AvailabilityRequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
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

        UserAvailability av = availabilityRepository.save(new UserAvailability(user, date, startTime, endTime));
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

}
