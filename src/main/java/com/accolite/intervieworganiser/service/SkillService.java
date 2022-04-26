package com.accolite.intervieworganiser.service;

import com.accolite.intervieworganiser.entities.Skill;
import com.accolite.intervieworganiser.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    /* get skill by ID */
    public Skill getById(Integer id){
        return skillRepository.getById(id);
    }

    /* get skills by IDs */
    public List<Skill> getByIds(List<Integer> ids){
        return skillRepository.findAllById(ids);
    }

    public List<Skill> getSkillsByName(String skillName){
        return skillRepository.getSkillsByName(skillName);
    }

    /* save new skill */
    public <S extends Skill> Skill save(Skill entity) {
        return skillRepository.save(entity);
    }

    /* get all skills */
    public List<Skill> getAll() {
        return skillRepository.findAll();
    }

}
