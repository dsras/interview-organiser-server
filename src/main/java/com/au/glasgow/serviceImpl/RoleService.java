package com.au.glasgow.serviceImpl;

import com.au.glasgow.entities.Role;
import com.au.glasgow.repository.RoleRepository;
import com.au.glasgow.service.ServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements ServiceInt<Role> {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getById(Integer id) {
        return roleRepository.getById(id);
    }

    @Override
    public Iterable<Role> getById(Iterable<Integer> ids) {
        return roleRepository.findAllById(ids);
    }

    @Override
    public <S extends Role> Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public <S extends Role> Iterable<S> saveAll(Iterable<S> roles) {
        return roleRepository.saveAll(roles);
    }

    /* custom methods */

    /* get role by name */
    public Role getByName(String name){
        return roleRepository.getByName(name);
    }
}
