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

/**
 * Provides handling of availability-related requests.
 */
@RestController
@RequestMapping("/availability")
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    @Autowired
    private UserService userService;

    /**
     * Gets username of principal (authenticated user).
     *
     * @return the principal's username
     */
    private String getPrincipalUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    /**
     * Adds new availability for a user.
     * <p>Takes availability details and adds to the database for user. </p>
     *
     * @param availability the new availability details
     * @return the newly saved availability
     */
    @PostMapping("/new")
    public List<UserAvailability> newAvailability(@RequestBody AvailabilityRequest availability) {
        AvailabilityRequestWrapper newAvailability = new AvailabilityRequestWrapper(availability,
                userService.findOne(getPrincipalUsername()));
        return availabilityService.save(newAvailability);
    }

    /**
     * Gets a user's availability.
     *
     * @return a list of user's availability
     */
    @GetMapping("/find")
    public ResponseEntity<List<UserAvailability>> getUserAvailability(){
        return new ResponseEntity<>(availabilityService.getUserAvailability(getPrincipalUsername()), HttpStatus.OK);
    }

    /**
     * Gets all availability (i.e., of all users).
     *
     * @return list of all availability
     */
    @GetMapping("/findAll")
    public ResponseEntity<List<UserAvailability>> getAllAvailability(){
        return new ResponseEntity<>(availabilityService.getAllAvailability(), HttpStatus.OK);
    }

    /**
     * Gets availability of all users with specified skills.
     * <p> Takes a list of skill IDs and returns the availability of users with these skills.</p>
     *
     * @param skillIds the list of skill IDs
     * @return a list of all availability of users with all specified skill IDs
     */
    @GetMapping("/findBySkills")
    public ResponseEntity<List<UserAvailability>> findBySkill(@RequestParam(name = "ids") List<Integer> skillIds){
        return new ResponseEntity<>(availabilityService.findBySkills(skillIds), HttpStatus.OK);
    }

}
