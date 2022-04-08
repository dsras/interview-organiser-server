package com.au.glasgow.serviceImpl;

import com.au.glasgow.entities.User;
import com.au.glasgow.entities.UserAvailability;
import com.au.glasgow.repository.AvailabilityRepository;
import com.au.glasgow.requestModels.AvailabilityRequest;
import com.au.glasgow.requestModels.AvailabilityRequestWrapper;
import com.au.glasgow.service.ServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class AvailabilityService implements ServiceInt<UserAvailability> {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Override
    public UserAvailability getById(Integer id) {
        return null;
    }

    @Override
    public Iterable<UserAvailability> getById(Iterable<Integer> ids) {
        return null;
    }

    @Override
    public <S extends UserAvailability> UserAvailability save(UserAvailability entity) { return null; }

    public AvailabilityRequest save(AvailabilityRequestWrapper newAvailability) {
        User user = newAvailability.getUser();
        LocalDate date = newAvailability.getDate();
        LocalTime startTime = newAvailability.getStartTime();
        LocalTime endTime = newAvailability.getEndTime();

        UserAvailability av = availabilityRepository.save(new UserAvailability(user, date, startTime, endTime));
        return new AvailabilityRequest(date, startTime, endTime);
    }

    @Override
    public <S extends UserAvailability> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }
}
