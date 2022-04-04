package com.au.glasgow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.au.glasgow.requestModels.AvailabilityRequest;
import com.au.glasgow.serviceImpl.AvailabilityService;
import com.au.glasgow.serviceImpl.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/availability")
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    @Autowired
    private UserService userService;

    /* test method */
    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome";
    }

    /* create new availability
    * should return availability (need to handle request & create availability) */
    @PostMapping("/new")
    @PreAuthorize("hasAnyRole('USER')")
    public AvailabilityRequest newAvailability(@RequestBody AvailabilityRequest newAvailability) {
//        User user = userService.getById(newAvailability.getUserId());
//        UserAvailability availability =
//        return availabilityService.save(newAvailability);
        return newAvailability;
    }

}
