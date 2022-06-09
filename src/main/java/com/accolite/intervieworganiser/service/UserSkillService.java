package com.accolite.intervieworganiser.service;

import com.accolite.intervieworganiser.entities.Skill;
import com.accolite.intervieworganiser.entities.User;
import com.accolite.intervieworganiser.entities.UserSkill;
import com.accolite.intervieworganiser.repository.UserSkillRepository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSkillService {

    private UserSkillRepository userSkillRepository;

    public UserSkillService(
        @Autowired UserSkillRepository userSkillRepository
    ) {
        this.userSkillRepository = userSkillRepository;
    }

    public <S extends UserSkill> UserSkill save(UserSkill entity) {
        return userSkillRepository.save(entity);
    }

    public List<Integer> findBySkills(List<Integer> skillIds) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println(skillIds);
        List<Integer> allTheSkillIds = new ArrayList<>();
        for( Integer element : skillIds){
            System.out.println(element);
            int rem = (element-1)%3;
            allTheSkillIds.add(element);
            for (int i = 0; i < rem; i++) {
                allTheSkillIds.add(element-(i+1));
            }
        }
        for (Integer element: allTheSkillIds) {
            System.out.println(element);
        }

        List<Integer> myUsers = userSkillRepository.findBySkills(skillIds);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        return myUsers;
    }

    public List<Skill> findByUser(String username) {
        return userSkillRepository.findByUser(username);
    }
}
