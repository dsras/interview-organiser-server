package com.au.glasgow.serviceImpl;

import com.au.glasgow.entities.Skill;
import com.au.glasgow.repository.SkillRepository;
import com.au.glasgow.service.ServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    public List<Skill> getByName(String name){
        return skillRepository.getSkillsByName(name);
    }

    public List<Skill> findAll(){
        return skillRepository.findAll();
    }

    public Skill getById(Integer id){return skillRepository.getById(id);}
}
