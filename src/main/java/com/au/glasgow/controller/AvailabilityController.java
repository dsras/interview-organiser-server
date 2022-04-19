package com.au.glasgow.controller;

import com.au.glasgow.dto.AvailabilityRequestWrapper;
import com.au.glasgow.entities.User;
import com.au.glasgow.entities.UserAvailability;
import org.springframework.beans.factory.annotation.Autowired;
import com.au.glasgow.dto.AvailabilityRequest;
import com.au.glasgow.service.AvailabilityService;
import com.au.glasgow.service.UserService;
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

    /* add new availability for current user */
    @PostMapping("/new")
    public AvailabilityRequest newAvailability(@RequestBody AvailabilityRequest newAvailability) {
        AvailabilityRequestWrapper wrapper = new AvailabilityRequestWrapper(newAvailability,
                userService.findOne(getPrincipalUsername()));
        return availabilityService.save(wrapper);
    }

    /* get current user's availability */
    @GetMapping("/find")
    public ResponseEntity<List<AvailabilityRequest>> getUserAvailability(){
        return new ResponseEntity<>(availabilityService.getUserAvailability(getPrincipalUsername()), HttpStatus.OK);
    }

    /* get all users availability slots - called by recruiter */
    @GetMapping("/findAll")
    public ResponseEntity<List<AvailabilityRequest>> getAllAvailability(){
        return new ResponseEntity<>(availabilityService.getAllAvailability(), HttpStatus.OK);
    }

    /* clear all availability - for Thorfinn testing */
    @GetMapping("/clear")
    public void clearAvailability(){
        availabilityService.clear();
    }

    /* get users with required skills - called by recruiter */
    @GetMapping("/findBySkills")
    public ResponseEntity<List<AvailabilityRequestWrapper>> findBySkill(@RequestParam(name = "ids") List<Integer> skillIds){
        return new ResponseEntity<>(availabilityService.findBySkills(skillIds), HttpStatus.OK);
    }

}
