package com.au.glasgow.controller;

import com.au.glasgow.requestModels.AvailabilityRequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.au.glasgow.requestModels.AvailabilityRequest;
import com.au.glasgow.serviceImpl.AvailabilityService;
import com.au.glasgow.serviceImpl.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/availability")
public class AvailabilityController {

    /* testing stuff
    * return SecurityContextHolder.getContext().getAuthentication().toString(); */
    private String username = "emer.sweeney@accolitedigital.com";
    /* end of testing stuff */

    @Autowired
    private AvailabilityService availabilityService;

    @Autowired
    private UserService userService;

    /* add new availability for user */
    @PostMapping("/new")
    public AvailabilityRequest newAvailability(@RequestBody AvailabilityRequest newAvailability) {
        AvailabilityRequestWrapper wrapper = new AvailabilityRequestWrapper(newAvailability, userService.findOne(username));
        return availabilityService.save(wrapper);
    }

    /* get user's availability */
    @GetMapping("/find")
    public ResponseEntity<List<AvailabilityRequest>> getAvailability(){
        return new ResponseEntity<>(availabilityService.get(username), HttpStatus.OK);
    }

}
