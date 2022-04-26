package com.accolite.intervieworganiser.service;

import com.accolite.intervieworganiser.entities.Role;
import com.accolite.intervieworganiser.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService{

    @Autowired
    private RoleRepository roleRepository;

    /* get role by name */
    public Role getByName(String name){
        return roleRepository.getByName(name);
    }
}
