package com.au.glasgow;

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

    @GetMapping("")
    public List<ResponseEntity<Skill>> allSkills(){
        return null;
    }

    @PostMapping("")
    public ResponseEntity<Skill> newSkill(@RequestBody Skill newSkill){
        return new ResponseEntity<Skill>(skillService.save(newSkill), HttpStatus.CREATED);
    }
}
