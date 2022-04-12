package com.au.glasgow.service;

import com.au.glasgow.entities.Role;
import com.au.glasgow.repository.RoleRepository;
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
