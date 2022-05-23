package com.accolite.intervieworganiser.controller;

import com.accolite.intervieworganiser.entities.Skill;
import com.accolite.intervieworganiser.entities.UserSkill;
import com.accolite.intervieworganiser.service.SkillService;
import com.accolite.intervieworganiser.service.UserService;
import com.accolite.intervieworganiser.service.UserSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Provides handling of user-skill requests
 */
@RestController
@RequestMapping("/user-skills")
public class UserSkillController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserSkillService userSkillService;

    @Autowired
    private SkillService skillService;

    /**
     * Gets user's skills.
     *
     * @return list of user's skills
     */
    @GetMapping("/{username}")
    public ResponseEntity<List<Skill>> findSkill(@PathVariable("username") String username){
        return new ResponseEntity<>(userSkillService.findByUser(username), HttpStatus.OK);
    }

    /**
     * Adds a skill to user's profile.
     *
     * @param newSkillId the integer ID of the skill being added to user profile
     * @return newSkillId
     */
    @PostMapping("/{username}")
    public Integer newSkill(@RequestBody Integer newSkillId, @PathVariable("username") String username){
        userSkillService.save(new UserSkill(userService.findOne(username),
                skillService.getById(newSkillId)));
        return newSkillId;
    }
}
