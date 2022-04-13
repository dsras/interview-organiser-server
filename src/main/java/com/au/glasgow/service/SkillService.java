package com.au.glasgow.service;

import com.au.glasgow.entities.Skill;
import com.au.glasgow.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    /* save new skill */
    public <S extends Skill> Skill save(Skill entity) {
        return skillRepository.save(entity);
    }

    /* get all skills */
    public List<Skill> getAll() {
        return skillRepository.findAll();
    }

    /* get all skills by name */
    public List<Skill> getSkillsByName(String name){
        return skillRepository.getSkillsByName(name);
    }

    /* get skill of specified name and level */
    public Skill getLevel(String name, String level){
        return skillRepository.getLevel(name, level);
    }

    /* for a list of skills, get all other appropriate skills (ie get higher levels of those skills) */
    public List<Skill> getAppropriateLevels(Skill skill){
        List<Skill> appropriateSkills = new ArrayList<>();
        appropriateSkills.add(skill);
        /* get Senior level of skill */
        if (!skill.getSkillLevel().equals("Senior")){
            appropriateSkills.add(getLevel(skill.getSkillName(), "Senior"));
        }
        /* if skill is Junior, get Intermediate level */
        if (skill.getSkillLevel().equals("Junior")){
            appropriateSkills.add(getLevel(skill.getSkillName(), "Intermediate"));
        }
        return appropriateSkills;
    }

}
