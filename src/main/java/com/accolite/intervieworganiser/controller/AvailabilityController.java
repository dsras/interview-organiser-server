package com.accolite.intervieworganiser.controller;

import com.accolite.intervieworganiser.dto.AvailabilityRequest;
import com.accolite.intervieworganiser.dto.AvailabilityWrapper;
import com.accolite.intervieworganiser.dto.DateRange;
import com.accolite.intervieworganiser.entities.UserAvailability;
import com.accolite.intervieworganiser.service.AvailabilityService;
import com.accolite.intervieworganiser.service.UserService;

import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Provides handling of availability-related requests.
 */
@RestController
public class AvailabilityController {

    private AvailabilityService availabilityService;
    private UserService userService;

    /**
     * Parameterised constructor.
     *
     * @param availabilityService availability service layer
     * @param userService user service layer
     */
    public AvailabilityController(
        @Autowired AvailabilityService availabilityService,
        @Autowired UserService userService
    ) {
        this.availabilityService = availabilityService;
        this.userService = userService;
    }


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/availability/delete")
    public void deleteAvailability(
            @Valid @RequestBody Integer id
    ){
        availabilityService.delete(id);
    }


    //@PreAuthorize("hasRole('USER')")
    @PostMapping("/availability/{username}/range")
    public ResponseEntity<List<UserAvailability>> getAvailabilityInRange(
            @PathVariable("username") String username,
            @Valid @RequestBody DateRange range
    ) {
        return new ResponseEntity<List<UserAvailability>>(
                availabilityService.getByRange(username, range.getStart(), range.getEnd()),
                HttpStatus.OK
        );
    }

    /**
     * Adds new availability for a user.
     * <p>Takes availability details and adds to the database for user.</p>
     *
     * @param availability the new availability details
     * @return the newly saved availability
     */
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/availability/{username}")
    public List<UserAvailability> addUserAvailability(
        @PathVariable("username") String username,
        @Valid @RequestBody AvailabilityRequest availability
    ) {
        AvailabilityWrapper newAvailability = new AvailabilityWrapper(
            availability,
            userService.findOne(username)
        );
        return availabilityService.save(newAvailability);
    }

    /**
     * Gets a user's availability.
     *
     * @return a list of user's availability
     */
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/availability/{username}")
    public ResponseEntity<List<UserAvailability>> getUserAvailability(
        @PathVariable("username") String username
    ) {
        return new ResponseEntity<>(
            availabilityService.getUserAvailability(username),
            HttpStatus.OK
        );
    }

    /**
     * Gets availability of all users can can be filtered by specified skills.
     * <p> Takes an optional list of skill IDs and returns the availability of users with these skills,
     * or all availability if no skills specified. </p>
     *
     * @param skillIds the list of skill IDs
     * @return a list of all availability of users with all specified skill IDs
     */
    @PreAuthorize("hasRole('ADMIN', 'RECRUITER')")
    @GetMapping("/availability")
    public ResponseEntity<List<UserAvailability>> getAvailability(
        @RequestParam(required = false, name = "ids") List<Integer> skillIds
    ) {
        if (skillIds == null || skillIds.isEmpty()) {
            return new ResponseEntity<>(
                availabilityService.getAllAvailability(),
                HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
            availabilityService.findBySkills(skillIds),
            HttpStatus.OK
        );
    }
}
