package com.au.glasgow.service;

import com.au.glasgow.entities.User;
import com.au.glasgow.repository.UserRepository;
import com.au.glasgow.requestModels.AvailableUsersRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements ServiceInt<User>{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getById(Integer id) {
        return userRepository.getById(id);
    }

    @Override
    public Iterable<User> getById(Iterable<Integer> ids) {
        return null;
    }

    @Override
    public <S extends User> User save(User entity) {
        return userRepository.save(entity);
    }

    @Override
    public <S extends User> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }


    /* custom methods */


    /* getAvailableUsers
    takes an AvailableUsersRequest object
    finds skills by ID
    finds interviewers that
    (1) have these skills
    (2) have availability on this date between these times
    (3) do not have interviews booked on this date between these times */
    public List<User> getAvailableUsers(AvailableUsersRequest availableUsersRequest){

        return null;
    }

}
