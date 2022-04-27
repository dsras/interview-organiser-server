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

    /* save new skill */
    public <S extends Skill> Skill save(Skill entity) {
        return skillRepository.save(entity);
    }

    /* get all skills */
    public List<Skill> getAll() {
        return skillRepository.findAll();
    }

}
