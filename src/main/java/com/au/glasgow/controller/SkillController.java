package com.au.glasgow.controller;

import com.au.glasgow.entities.Skill;
import com.au.glasgow.serviceImpl.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/skill")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECRUITER', 'USER')")
    public ResponseEntity<List<Skill>> newSkill(@RequestParam(value="name") String name){
        return new ResponseEntity<>(skillService.getByName(name),HttpStatus.OK);
    }
}
