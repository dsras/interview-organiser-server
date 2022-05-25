package com.accolite.intervieworganiser.service;

import com.accolite.intervieworganiser.entities.Skill;
import com.accolite.intervieworganiser.repository.SkillRepository;
import java.util.List;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkillService {

    private SkillRepository skillRepository;

    /**
     * Parameterised constructor.
     *
     * @param skillRepository skill data access layer
     */
    public SkillService(@Autowired SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    /* get skill by ID */
    public Skill getById(Integer id) {
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
