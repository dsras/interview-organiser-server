package com.accolite.intervieworganiser.service;

import com.accolite.intervieworganiser.entities.Role;
import com.accolite.intervieworganiser.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    /**
     * Parameterised constructor.
     *
     * @param roleRepository role data access layer
     */
    public RoleService(@Autowired RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /* get role by name */
    public Role getByName(String name) {
        return roleRepository.getByName(name);
    }
    public void setNewUserRole(Integer user_id){
        System.out.println(user_id);
        roleRepository.setNewUserRole(roleRepository.getMaxIdInUserRoles()+1, user_id);
    }
}
