package com.au.glasgow;

import com.au.glasgow.entities.User;
import com.au.glasgow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    /* get user by username */
    @GetMapping("/user")
    public ResponseEntity<User> getUser(@RequestParam(value="username", required = true) String username){
        return new ResponseEntity<User>(userService.getByUsername(username), HttpStatus.OK);
    }

    /* create new user */
    @PostMapping("/new")
    public ResponseEntity<User> newUser(@RequestBody User newUser) {
        return new ResponseEntity<User>(userService.save(newUser), HttpStatus.CREATED);
    }

//    @PostMapping("/users")
//    public List<User> getAvailableUsers(@RequestBody AvailableUsersRequest availableUsersRequest){
//        /*
//        need a service method to call
//        that takes this request
//        finds skills by ID
//        finds interviewers that
//        (1) have these skills
//        (2) have availability on this date between these times
//        (3) do not have interviews booked on this date between these times
//         */
//        return null;
//    }
}
