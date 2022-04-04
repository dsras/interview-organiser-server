package com.au.glasgow.controller;

import com.au.glasgow.entities.User;
import com.au.glasgow.requestModels.AvailableUsersRequest;
import com.au.glasgow.serviceImpl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;

/*
user controller
handles user-related requests
 */

@RestController
@RequestMapping("/users")
public class UserController {
    /*
    requests:
    GET:
    - profile details
    - skills
    - availability (for time period)
    - scheduled interviews (for time period)

    POST
    - create user (? unsure how this happens atm)
    - add skill
    - add availability

    PUT
    - update skill

    extra:
    PUT
    - edit availability

    DELETE
    - delete availability


    nb: all editing of profile details is done through Google account
     */

    @Autowired
    private UserService userService;

    /* test method */
    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome";
    }


    /* get user by username
    * access: all */
    @GetMapping("/user")
    @PreAuthorize("hasRole('ADMIN', 'RECRUITER', 'USER')")
    public User getUser(@RequestParam(value="username", required = true) String username){
        return userService.getByUsername(username);
    }

    /* create new user */
    @PostMapping("/new")
    @PermitAll
    public User newUser(@RequestBody User newUser) {
        return userService.save(newUser);
    }

    /* get interviewers available for interview
    * access: only admins and recruiters */
    @PostMapping("/available")
    @PreAuthorize("hasAnyRole('ADMIN','RECRUITER')")
    public AvailableUsersRequest getAvailableUsers(@RequestBody AvailableUsersRequest availableUsersRequest){
        AvailableUsersRequest test = availableUsersRequest;
        /*
        need a service method to call
        that takes this request
        finds skills by ID
        finds interviewers that
        (1) have these skills
        (2) have availability on this date between these times
        (3) do not have interviews booked on this date between these times
         */
        return test;
    }
}
