package com.au.glasgow.controller;

import com.au.glasgow.service.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    /* test method */
    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome";
    }

//
//    /* get user by username */
//    @GetMapping("/user")
//    public ResponseEntity<User> getUser(@RequestParam(value="username", required = true) String username){
//        return new ResponseEntity<User>(userService.getByUsername(username), HttpStatus.OK);
//    }
//
//    /* create new user */
//    @PostMapping("/new")
//    public ResponseEntity<User> newUser(@RequestBody User newUser) {
//        return new ResponseEntity<User>(userService.save(newUser), HttpStatus.CREATED);
//    }

}
