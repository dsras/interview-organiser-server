package com.au.glasgow.controller;

import com.au.glasgow.entities.Skill;
import com.au.glasgow.serviceImpl.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skills")
public class SkillController {

    @Autowired
    private SkillService skillService;

    /* get all skills */
    @GetMapping("")
    public List<Skill> allSkills(){
        return null;
    }

    /* add new skill to db */
    @PostMapping("/new")
    public Skill newSkill(@RequestBody Skill newSkill){
        return skillService.save(newSkill);
    }
}
