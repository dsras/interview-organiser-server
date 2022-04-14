package com.au.glasgow.service;

import com.au.glasgow.entities.Skill;
import com.au.glasgow.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    public Skill getById(Integer id){
        return skillRepository.getById(id);
    }

    public <S extends Skill> Skill save(Skill entity) {
        return skillRepository.save(entity);
    }

    public List<Skill> getAll() {
        return skillRepository.findAll();
    }

    public List<Skill> getSkillsByName(String skillName){
        return skillRepository.getSkillsByName(skillName);
    }

}
