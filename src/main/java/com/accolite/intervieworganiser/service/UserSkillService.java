package com.accolite.intervieworganiser.service;

import com.accolite.intervieworganiser.entities.User;
import com.accolite.intervieworganiser.entities.UserSkill;
import com.accolite.intervieworganiser.repository.UserRepository;
import com.accolite.intervieworganiser.repository.UserSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSkillService {

    @Autowired
    private UserSkillRepository userSkillRepository;

    public UserSkillService(UserSkillRepository userSkillRepository){
        this.userSkillRepository=userSkillRepository;
    }

    public <S extends UserSkill> UserSkill save(UserSkill entity) {
        return userSkillRepository.save(entity);
    }

    public List<User> findBySkills(List<Integer> skillIds, long listSize){
        return userSkillRepository.findBySkills(skillIds, listSize);
    }
}
