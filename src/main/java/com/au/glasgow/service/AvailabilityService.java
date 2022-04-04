package com.au.glasgow.service;

import com.au.glasgow.repository.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;

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
        return null;
    }

    @Override
    public <S extends UserAvailability> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }
}
