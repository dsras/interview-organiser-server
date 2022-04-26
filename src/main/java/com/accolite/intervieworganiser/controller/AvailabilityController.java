package com.accolite.intervieworganiser.controller;

import com.accolite.intervieworganiser.dto.AvailabilityRequest;
import com.accolite.intervieworganiser.entities.UserAvailability;
import com.accolite.intervieworganiser.service.AvailabilityService;
import com.accolite.intervieworganiser.service.UserService;
import com.accolite.intervieworganiser.dto.AvailabilityRequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/availability")
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    @Autowired
    private UserService userService;

    /* get username of logged in user */
    private String getPrincipalUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    /* add new availability slots for current user */
    @PostMapping("/new")
    public List<UserAvailability> newAvailability(@RequestBody AvailabilityRequest newAvailability) {
        AvailabilityRequestWrapper wrapper = new AvailabilityRequestWrapper(newAvailability,
                userService.findOne(getPrincipalUsername()));
        return availabilityService.save(wrapper);
    }

    /* get current user's availability */
    @GetMapping("/find")
    public ResponseEntity<List<UserAvailability>> getUserAvailability(){
        return new ResponseEntity<>(availabilityService.getUserAvailability(getPrincipalUsername()), HttpStatus.OK);
    }

    /* get all users availability slots - called by recruiter */
    @GetMapping("/findAll")
    public ResponseEntity<List<UserAvailability>> getAllAvailability(){
        return new ResponseEntity<>(availabilityService.getAllAvailability(), HttpStatus.OK);
    }

    /* get users with required skills - called by recruiter */
    @GetMapping("/findBySkills")
    public ResponseEntity<List<UserAvailability>> findBySkill(@RequestParam(name = "ids") List<Integer> skillIds){
        return new ResponseEntity<>(availabilityService.findBySkills(skillIds), HttpStatus.OK);
    }


    /**** DELETE ONCE TESTING IS DONE ****/
    /* clear all availability - for Thorfinn testing */
    @GetMapping("/clear")
    public void clearAvailability(){
        availabilityService.clear();
    }

}
