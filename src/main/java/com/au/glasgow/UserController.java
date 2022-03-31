package com.au.glasgow;

import com.au.glasgow.entities.Skill;
import com.au.glasgow.entities.User;
import com.au.glasgow.service.SkillService;
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


    nb: all editing of profile details is done through google account
     */

    @Autowired
    private UserService userService;

    @Autowired
    private SkillService skillService;

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome";
    }

    @PostMapping("/skills")
    public ResponseEntity<Skill> newSkill(@RequestBody Skill newSkill){
        return new ResponseEntity<Skill>(skillService.save(newSkill), HttpStatus.CREATED);
    }


//    @GetMapping("/user")
//    public User getUser(@RequestParam(value="email", required = true) String email){
//        User user = userService.getByEmail(email);
//        return user;
//    }

    @PostMapping("/users")
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
