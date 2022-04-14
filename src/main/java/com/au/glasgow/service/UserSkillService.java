package com.au.glasgow.service;

import com.au.glasgow.entities.UserSkill;
import com.au.glasgow.repository.UserSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSkillService {

    @Autowired
    private UserSkillRepository userSkillRepository;

    public UserSkill getById(Integer id){
        return userSkillRepository.getById(id);
    }

    public <S extends UserSkill> UserSkill save(UserSkill entity) {
        return userSkillRepository.save(entity);
    }
}