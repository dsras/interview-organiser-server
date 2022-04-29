package com.accolite.intervieworganiser.service;

import com.accolite.intervieworganiser.entities.Skill;
import com.accolite.intervieworganiser.entities.User;
import com.accolite.intervieworganiser.repository.SkillRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Id;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SkillServiceTest {

    @Mock
    SkillRepository skillRepository;

    SkillService skillService;

    Skill newSkill;

    List<Skill> newSkillList;

    final String skillName = "testSkill";
    final String skillLevel = "testLevel";


    @BeforeEach
    void initUseCase() {
        /* initialise skill service */
        skillService = new SkillService(skillRepository);
    }

    @BeforeEach
    void setup(){ newSkill = new Skill(skillName, skillLevel);
    }

    @BeforeEach
    void listSetup(){ newSkillList = new ArrayList<Skill>();
        newSkillList.add(newSkill);
    }

    @Test
    void testSaveSkill() {
        /* return new skill to mock repository layer saving skill */
        when(skillRepository.save(any(Skill.class))).thenReturn(newSkill);
        /* ensure service saved new skill correctly */
        assertThat(skillService.save(newSkill).getSkillName()).isEqualTo(skillName);
    }

    //needs adjustment
    @Test
    void testGetById(){
        Integer id = skillService.save(newSkill).getId();
        /* return this skill to mock repository layer getting skill by Id */
        when(skillRepository.getById(any(Integer.class))).thenReturn(newSkill);
        /* ensure service correctly gets skill by Id */
        Skill mySkill = skillService.getById(id);
        assertThat(mySkill.getSkillName()).isEqualTo(skillName);
    }

    //needs adjustment
    @Test
    void testGetAll(){
        when(skillService.getAll()).thenReturn(newSkillList);
        assertThat(skillService.getAll()).isEqualTo(newSkillList);
    }
}
