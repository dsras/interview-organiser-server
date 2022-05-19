package com.accolite.intervieworganiser.controller;

import com.accolite.intervieworganiser.dto.*;
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
    @PostMapping("/new")
    @PreAuthorize("hasAnyRole('RECRUITER', 'ADMIN')")
    public ResponseEntity<InterviewResponse> newInterview(@Valid @RequestBody InterviewRequest newInterview) {
        InterviewRequestWrapper wrapper = new InterviewRequestWrapper
                (newInterview, userService.findOne(getPrincipalUsername()));
        return new ResponseEntity<>(interviewService.save(wrapper), HttpStatus.CREATED);
    }

    /**
     * Gets all interviews an interviewer is participating in.
     *
     * @return a list of all interviews the interviewer was/is on panel for
     */
    @GetMapping("/findByInterviewer")
    @PreAuthorize("hasAnyRole('USER', 'RECRUITER', 'ADMIN')")
    public ResponseEntity<List<InterviewResponse>> findByInterviewer(@RequestParam("username") String username){
        return new ResponseEntity<>(interviewService.findByInterviewer(userService.findOne(username)), HttpStatus.OK);
    }

    /**
     * Gets all interviews a recruiter organised.
     *
     * @return a list of all interviews the recruiter organised
     */
    @GetMapping("/findByRecruiter")
    @PreAuthorize("hasAnyRole('RECRUITER', 'ADMIN')")
    public ResponseEntity<List<InterviewResponse>> findByRecruiter(@RequestParam("username") String username){
        return new ResponseEntity<>(interviewService.findByRecruiter
                (userService.findOne(username)), HttpStatus.OK);
    }

    /**
     * Gets all interviews.
     *
     * @return list of all interviews
     */
    @GetMapping("/findAll")
    @PreAuthorize("hasAnyRole('RECRUITER', 'ADMIN')")
    public ResponseEntity<List<Interview>> findAll(){
        return new ResponseEntity<>(interviewService.findAll(), HttpStatus.OK);
    }

    /**
     * Gets number of interviews with status 'Completed' an interviewer has completed.
     *
     * @return the number confirmed interviews that the interviewer was on panel for
     */
    @GetMapping("/findCompleted")
    @PreAuthorize("hasAnyRole('USER', 'RECRUITER', 'ADMIN')")
    public ResponseEntity<Integer> findCompleted(@RequestParam("username") String username){
        return new ResponseEntity<>(interviewService.findCompleted
                (userService.findOne(username)), HttpStatus.OK);
    }

    /**
     * Gets all interviews organised by a recruiter confirmed by interviewer.
     *
     * @return a list of interviews with status 'Completed' that recruiter has organised
     */
    @GetMapping("/findConfirmed")
    @PreAuthorize("hasAnyRole('RECRUITER', 'ADMIN')")
    public ResponseEntity<List<InterviewResponse>> findConfirmed(){
        return new ResponseEntity<>(interviewService.findConfirmed
                (userService.findOne(getPrincipalUsername())), HttpStatus.OK);
    }

    /**
     * Gets all interviews organised by a recruiter that candidate didn't show up for.
     *
     * @return a list of interviews with status 'Candidate No Show' that recruiter has organised
     */
    @GetMapping("/findCNS")
    @PreAuthorize("hasAnyRole('RECRUITER', 'ADMIN')")
    public ResponseEntity<List<InterviewResponse>> findCNS(){
        return new ResponseEntity<>(interviewService.findCNS
                (userService.findOne(getPrincipalUsername())), HttpStatus.OK);
    }

    /**
     * Gets all interviews organised by recruiter that panel didn't show up for.
     *
     * @return a list of interviews with status 'Panel No Show' that recruiter organised
     */
    @GetMapping("/findPNS")
    @PreAuthorize("hasAnyRole('RECRUITER', 'ADMIN')")
    public ResponseEntity<List<InterviewResponse>> findPNS(){
        return new ResponseEntity<>(interviewService.findPNS
                (userService.findOne(getPrincipalUsername())), HttpStatus.OK);
    }

    /**
     * Gets all interviews from past 28 days organised by a recruiter where candidate progressed.
     *
     * @return a list of interviews with outcome 'Progressed' that recruiter organised
     */
    @GetMapping("/findProgressed")
    @PreAuthorize("hasAnyRole('RECRUITER', 'ADMIN')")
    public ResponseEntity<List<InterviewResponse>> findProgressed(){
        return new ResponseEntity<>(interviewService.findProgressed
                (userService.findOne(getPrincipalUsername())), HttpStatus.OK);
    }

    /**
     * Gets all interviews from past 28 days organised by a recruiter where candidate didn't progress.
     *
     * @return a list of interviews with outcome 'Didn't Progress' that recruiter organised
     */
    @GetMapping("/findNotProgressed")
    @PreAuthorize("hasAnyRole('RECRUITER', 'ADMIN')")
    public ResponseEntity<List<InterviewResponse>> findNotProgressed(){
        return new ResponseEntity<>(interviewService.findNotProgressed
                (userService.findOne(getPrincipalUsername())), HttpStatus.OK);
    }

    /**
     * Gets all interviews from past 28 days organised by a recruiter where candidate was hired.
     *
     * @return a list of interviews with outcome 'Hired' that recruiter organised
     */
    @GetMapping("/findHired")
    @PreAuthorize("hasAnyRole('RECRUITER', 'ADMIN')")
    public ResponseEntity<List<InterviewResponse>> findHired(){
        return new ResponseEntity<>(interviewService.findHired
                (userService.findOne(getPrincipalUsername())), HttpStatus.OK);
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
}
