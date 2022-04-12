package com.au.glasgow.controller;

import com.au.glasgow.entities.Skill;
import com.au.glasgow.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skills")
public class SkillController {

    @Autowired
    private SkillService skillService;

    /* add new skill to db */
    @PostMapping("/new")
    public Skill newSkill(@RequestBody Skill newSkill){
        return skillService.save(newSkill);
    }

    //Get all skills
    @GetMapping("/findAll")
    public ResponseEntity<List<Skill>> newAllSkills(){
        return new ResponseEntity<>(skillService.getAll(),HttpStatus.OK);
    }
}
