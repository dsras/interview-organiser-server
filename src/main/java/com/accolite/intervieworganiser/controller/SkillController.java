package com.accolite.intervieworganiser.controller;

import com.accolite.intervieworganiser.entities.Skill;
import com.accolite.intervieworganiser.service.SkillService;
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
    public ResponseEntity<Skill> newSkill(@RequestBody Skill newSkill){
        return new ResponseEntity<>(skillService.save(newSkill), HttpStatus.CREATED);
    }

    //Get all skills
    @GetMapping("/findAll")
    public ResponseEntity<List<Skill>> newAllSkills(){
        return new ResponseEntity<>(skillService.getAll(),HttpStatus.OK);
    }
}