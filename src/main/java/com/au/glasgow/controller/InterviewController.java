package com.au.glasgow.controller;

import com.au.glasgow.dto.InterviewRequest;
import com.au.glasgow.dto.InterviewRequestWrapper;
import com.au.glasgow.dto.InterviewResponse;
import com.au.glasgow.dto.InterviewUpdate;
import com.au.glasgow.service.InterviewService;
import com.au.glasgow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interviews")
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    @Autowired
    private UserService userService;

    /* get username of logged in user */
    private String getPrincipalUsername(){
//        return SecurityContextHolder.getContext().getAuthentication().getName();
        return "emer.sweeney@accolitedigital.com";
    }

    /* create new interview */
    @PostMapping("/new")
    public ResponseEntity<InterviewResponse> newInterview(@RequestBody InterviewRequest newInterview) {
        InterviewRequestWrapper wrapper = new InterviewRequestWrapper
                (newInterview, userService.findOne(getPrincipalUsername()));
        return new ResponseEntity<>(interviewService.save(wrapper), HttpStatus.CREATED);
    }

    /* find all interviews the interviewer is involved in */
    @GetMapping("/findByInterviewer")
    public ResponseEntity<List<InterviewResponse>> findByInterviewer(){
        return new ResponseEntity<>(interviewService.findByInterviewer
                (userService.findOne(getPrincipalUsername())), HttpStatus.OK);
    }

    /* find all interviews the recruiter has organised */
    @GetMapping("/findByRecruiter")
    public ResponseEntity<List<InterviewResponse>> findByRecruiter(){
        return new ResponseEntity<>(interviewService.findByRecruiter
                (userService.findOne(getPrincipalUsername())), HttpStatus.OK);
    }

    /* find all interviews */
    @GetMapping("/findAll")
    public ResponseEntity<List<InterviewResponse>> findAll(){
        return new ResponseEntity<>(interviewService.findAll(), HttpStatus.OK);
    }

    /* find all interviews with status 'Confirmed' completed by the interviewer */
    @GetMapping("/findCompleted")
    public ResponseEntity<Integer> findCompleted(){
        return new ResponseEntity<>(interviewService.findCompleted
                (userService.findOne(getPrincipalUsername())), HttpStatus.OK);
    }

    /* find all interviews with status 'Confirmed' organised by the recruiter */
    @GetMapping("/findConfirmed")
    public ResponseEntity<List<InterviewResponse>> findConfirmed(){
        return new ResponseEntity<>(interviewService.findConfirmed
                (userService.findOne(getPrincipalUsername())), HttpStatus.OK);
    }

    /* find all interviews with status 'Candidate No Show' organised by the recruiter */
    @GetMapping("/findCNS")
    public ResponseEntity<List<InterviewResponse>> findCNS(){
        return new ResponseEntity<>(interviewService.findCNS
                (userService.findOne(getPrincipalUsername())), HttpStatus.OK);
    }

    /* find all interviews with status 'Panel No Show' organised by the recruiter */
    @GetMapping("/findPNS")
    public ResponseEntity<List<InterviewResponse>> findPNS(){
        return new ResponseEntity<>(interviewService.findPNS
                (userService.findOne(getPrincipalUsername())), HttpStatus.OK);
    }

    /* update interview status (Confirmed, Candidate No Show, Panel No Show) */
    @PostMapping("/updateStatus")
    public ResponseEntity<InterviewResponse> updateStatus(@RequestBody InterviewUpdate statusUpdate){
        return new ResponseEntity<>(interviewService.updateStatus
                (statusUpdate.getUpdate(), statusUpdate.getInterviewId()), HttpStatus.OK);
    }

    /* update interview outcome (Progressed, Hired, Didn't Progress) */
    @PostMapping("/updateOutcome")
    public ResponseEntity<InterviewResponse> updateOutcome(@RequestBody InterviewUpdate outcomeUpdate){
        return new ResponseEntity<>(interviewService.updateOutcome
                (outcomeUpdate.getUpdate(), outcomeUpdate.getInterviewId()), HttpStatus.OK);
    }
}
