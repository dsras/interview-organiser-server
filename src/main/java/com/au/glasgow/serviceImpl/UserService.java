package com.au.glasgow.serviceImpl;

import com.au.glasgow.entities.Role;
import com.au.glasgow.entities.User;
import com.au.glasgow.repository.UserRepository;
import com.au.glasgow.requestModels.AvailableUsersRequest;
import com.au.glasgow.service.ServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements ServiceInt<User> {

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
        return userRepository.getAvailableUser(availableUsersRequest);
    }

    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    public User getByUsername(String username){
        return userRepository.getByUsername(username);
    }

    public Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        userRepository.getRolesByUsername(user.getUsername()).forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        });
        return authorities;
    }
}
