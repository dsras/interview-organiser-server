package com.au.glasgow.serviceImpl;

import com.au.glasgow.entities.Skill;
import com.au.glasgow.repository.SkillRepository;
import com.au.glasgow.service.ServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkillService implements ServiceInt<Skill> {

    @Autowired
    private SkillRepository skillRepository;

    @Override
    public Skill getById(Integer id) {
        return null;
    }

    @Override
    public Iterable<Skill> getById(Iterable<Integer> ids) {
        return null;
    }

    @Override
    public <S extends Skill> Skill save(Skill entity) {
        return skillRepository.save(entity);
    }

    @Override
    public <S extends Skill> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }
}
