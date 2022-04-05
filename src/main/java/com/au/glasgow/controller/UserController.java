package com.au.glasgow.controller;

import com.au.glasgow.entities.User;
import com.au.glasgow.requestModels.AvailableUsersRequest;
import com.au.glasgow.serviceImpl.UserService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.security.Principal;

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

     */

    @Autowired
    private UserService userService;

    /* test method */
    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(){
        return new ResponseEntity<String>("Welcome", HttpStatus.OK);
//        return SecurityContextHolder.getContext().getAuthentication();
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
