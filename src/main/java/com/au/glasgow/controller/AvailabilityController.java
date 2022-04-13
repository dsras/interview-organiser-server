package com.au.glasgow.controller;

import com.au.glasgow.dto.AvailabilityRequestWrapper;
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

    /* add new availability for user */
    @PostMapping("/new")
    public AvailabilityRequest newAvailability(@RequestBody AvailabilityRequest newAvailability) {
        AvailabilityRequestWrapper wrapper = new AvailabilityRequestWrapper(newAvailability,
                userService.findOne(getPrincipalUsername()));
        return availabilityService.save(wrapper);
    }

    /* get user's availability */
    @GetMapping("/find")
    public ResponseEntity<List<AvailabilityRequest>> getAvailability(){
        return new ResponseEntity<>(availabilityService.get(getPrincipalUsername()), HttpStatus.OK);
    }

    /* clear all availability - for Thorfinn testing */
    @GetMapping("/clear")
    public void clearAvailability(){
        availabilityService.clear();
    }

//    /* get skill by ID */
//    @GetMapping("/findBySkill")
//    public List<UserAvailability> findBySkill(Integer skillId){
//        return availabilityService.findBySkill(skillId);
//    }

}
