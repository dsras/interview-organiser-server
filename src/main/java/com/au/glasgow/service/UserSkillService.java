package com.au.glasgow.service;

import com.au.glasgow.entities.Skill;
import com.au.glasgow.entities.User;
import com.au.glasgow.entities.UserSkill;
import com.au.glasgow.repository.UserSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSkillService {

    @Autowired
    private UserSkillRepository userSkillRepository;

    @Autowired
    private SkillService skillService;

    public UserSkill getById(Integer id){
        return userSkillRepository.getById(id);
    }

    public <S extends UserSkill> UserSkill save(UserSkill entity) {
        return userSkillRepository.save(entity);
    }

    List<User> findBySkills(List<Integer> skillIds, long listSize){
        return userSkillRepository.findBySkills(skillIds, listSize);
    }
}
