package com.au.glasgow.serviceImpl;

import com.au.glasgow.entities.UserAvailability;
import com.au.glasgow.repository.AvailabilityRepository;
import com.au.glasgow.service.ServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public <S extends UserAvailability> UserAvailability save(UserAvailability entity) {

        return availabilityRepository.save(entity);
    }

    @Override
    public <S extends UserAvailability> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }
}
