package com.accolite.intervieworganiser.controller;

import com.accolite.intervieworganiser.dto.*;
import com.accolite.intervieworganiser.entities.UserAvailability;
import com.accolite.intervieworganiser.service.InterviewService;
import com.accolite.intervieworganiser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Provides handling of interview-related requests.
 */
@RestController
@RequestMapping("/interviews")
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

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
     * Adds a new interview to the database.
     * <p> Takes a new interview and saves to the database, returning the newly created interview. </p>
     *
     * @param newInterview the interview details
     * @return the interview newly added to the database
     */
    @PostMapping("/{username}")
    @PreAuthorize("hasAnyRole('RECRUITER', 'ADMIN')")
    public ResponseEntity<InterviewResponse> newInterview(@PathVariable("username") String username,
                                                          @Valid @RequestBody InterviewRequest newInterview) {
        InterviewRequestWrapper wrapper = new InterviewRequestWrapper
                (newInterview, userService.findOne(username));
        return new ResponseEntity<>(interviewService.save(wrapper), HttpStatus.CREATED);
    }

    /**
     * Gets all interviews an interviewer is participating in.
     *
     * @return a list of all interviews the interviewer was/is on panel for
     */
    @GetMapping("/{username}")
    @PreAuthorize("hasAnyRole('USER', 'RECRUITER', 'ADMIN')")
    public ResponseEntity<List<InterviewResponse>> findByInterviewer(@PathVariable("username") String username){
        return new ResponseEntity<>(interviewService.findByInterviewer(userService.findOne(username)), HttpStatus.OK);
    }

    /**
     * Gets all interviews a recruiter organised.
     *
     * @return a list of all interviews the recruiter organised
     */
    @GetMapping("/organiser/{username}")
    @PreAuthorize("hasAnyRole('RECRUITER', 'ADMIN')")
    public ResponseEntity<List<InterviewResponse>> findByRecruiter(@PathVariable("username") String username){
        return new ResponseEntity<>(interviewService.findByRecruiter
                (userService.findOne(username)), HttpStatus.OK);
    }

    /**
     * Gets all interviews.
     *
     * @return list of all interviews
     */
    @GetMapping("/")
    @PreAuthorize("hasAnyRole('RECRUITER', 'ADMIN')")
    public ResponseEntity<List<Interview>> findAll(){
        return new ResponseEntity<>(interviewService.findAll(), HttpStatus.OK);
    }

    /**
     * Gets number of interviews with status 'Completed' an interviewer has completed.
     *
     * @return the number confirmed interviews that the interviewer was on panel for
     */
    @GetMapping("/{username}/completed")
    @PreAuthorize("hasAnyRole('USER', 'RECRUITER', 'ADMIN')")
    public ResponseEntity<Integer> findCompleted(@PathVariable("username") String username){
        return new ResponseEntity<>(interviewService.findCompleted
                (userService.findOne(username)), HttpStatus.OK);
    }

    /**
     * Gets all interviews organised by a recruiter confirmed by interviewer.
     *
     * @return a list of interviews with status 'Completed' that recruiter has organised
     */
    @GetMapping("/{username}/status/{status}")
    @PreAuthorize("hasAnyRole('RECRUITER', 'ADMIN')")
    public ResponseEntity<List<InterviewResponse>> findByStatus(@PathVariable("username") String username,
                                                                 @PathVariable("status") String status){
        return new ResponseEntity<>(interviewService.findByStatus(userService.findOne(username), status),
                HttpStatus.OK);
    }

    /**
     * Gets all interviews from past 28 days organised by a recruiter where candidate progressed.
     *
     * @return a list of interviews with outcome 'Progressed' that recruiter organised
     */
    @GetMapping("/{username}/outcome/{outcome}")
//    @PreAuthorize("hasAnyRole('RECRUITER', 'ADMIN')")
    public ResponseEntity<List<InterviewResponse>> findByOutcome(@PathVariable("username") String username,
                                                                 @PathVariable("outcome") String outcome){
        return new ResponseEntity<>(interviewService.findByOutcome
                (userService.findOne(username), outcome), HttpStatus.OK);
    }

    /**
     * Updates interview status.
     * <p>Takes interview update detailing whether interview has been completed, candidate didn't show up
     * or panel didn't show up.</p>
     *
     * @param statusUpdate update containing new status e.g., Confirmed
     * @return the interview with newly updated status
     */
    @PostMapping("/updateStatus")
    @PreAuthorize("hasAnyRole('USER', 'RECRUITER', 'ADMIN')")
    public ResponseEntity<InterviewResponse> updateStatus(@Valid @RequestBody InterviewUpdate statusUpdate){
        return new ResponseEntity<>(interviewService.updateStatus
                (statusUpdate.getUpdate(), statusUpdate.getInterviewId()), HttpStatus.OK);
    }

    /**
     * Updates interview outcome.
     * <p>Takes interview outcome detailing whether candidate progressed, was hired or didn't progress. </p>
     *
     * @param outcomeUpdate update containing new outcome e.g., Hired
     * @return the interview with the newly updated outcome
     */
    @PostMapping("/updateOutcome")
    @PreAuthorize("hasAnyRole('RECRUITER', 'ADMIN')")
    public ResponseEntity<InterviewResponse> updateOutcome(@Valid @RequestBody InterviewUpdate outcomeUpdate){
        return new ResponseEntity<>(interviewService.updateOutcome
                (outcomeUpdate.getUpdate(), outcomeUpdate.getInterviewId()), HttpStatus.OK);
    }

    /**
     * Gets suitable interviewers.
     * <p> Takes a request for interviewers which details skills, dates and times and gets list of interviewers
     * with skills and availability to match. </p>
     *
     * @param findInterviewersRequest formatted request for suitable interviewers
     * @return list of user availability
     */
    @PostMapping("/findInterviewers")
    public ResponseEntity<List<UserAvailability>> findInterviewers(@RequestBody FindInterviewersRequest findInterviewersRequest) {
        return new ResponseEntity<>(userService.getAvailableInterviewers(findInterviewersRequest), HttpStatus.OK);
    }
}
