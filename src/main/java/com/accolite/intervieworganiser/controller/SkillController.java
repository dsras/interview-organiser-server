package com.accolite.intervieworganiser.controller;

import com.accolite.intervieworganiser.entities.Skill;
import com.accolite.intervieworganiser.service.SkillService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Provides handling of skill-related requests.
 */
@RestController
@RequestMapping("/skills")
public class SkillController {

    private SkillService skillService;

    /**
     * Parameterised constructor.
     *
     * @param skillService skill service layer
     */
    public SkillController(@Autowired SkillService skillService) {
        this.skillService = skillService;
    }

    /**
     * Adds new skill to the database.
     * <p> Takes new skill (name and description) and saves to the database, returning the new skill with ID. </p>
     *
     * @param newSkill the new skill
     * @return the skill newly added to the database
     */
    @PostMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Skill> newSkill(@RequestBody Skill newSkill) {
        return new ResponseEntity<>(
            skillService.save(newSkill),
            HttpStatus.CREATED
        );
    }

    /**
     * Gets all skills from the database.
     *
     * @return a list of all skills
     */
    @GetMapping("/findAll")
    @PreAuthorize("hasAnyRole('USER', 'RECRUITER', 'ADMIN')")
    public ResponseEntity<List<Skill>> allSkills() {
        return new ResponseEntity<>(skillService.getAll(), HttpStatus.OK);
    }
}
