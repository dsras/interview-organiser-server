package com.au.glasgow.service;

import com.au.glasgow.repository.SkillRepository;
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
