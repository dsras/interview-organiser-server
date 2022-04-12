package com.au.glasgow.controller;

import com.au.glasgow.dto.InterviewRequest;
import com.au.glasgow.dto.InterviewRequestWrapper;
import com.au.glasgow.dto.InterviewResponse;
import com.au.glasgow.service.InterviewService;
import com.au.glasgow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interviews")
public class InterviewController {

    /* testing stuff
     * return SecurityContextHolder.getContext().getAuthentication().toString(); */
    private String username = "emer.sweeney@accolitedigital.com";
    /* end of testing stuff */

    @Autowired
    private InterviewService interviewService;

    @Autowired
    private UserService userService;


    /* confirm interview has happened */
    @GetMapping("confirm")
    public ResponseEntity<InterviewResponse> confirmInterview(@RequestParam(value="id") Integer id){
        return new ResponseEntity<>(interviewService.confirm(id), HttpStatus.OK);
    }

    /* create new interview */
    @PostMapping("/new")
    public ResponseEntity<InterviewResponse> newInterview(@RequestBody InterviewRequest newInterview) {
        InterviewRequestWrapper wrapper = new InterviewRequestWrapper(newInterview, userService.findOne(username));
        return new ResponseEntity<>(interviewService.save(wrapper), HttpStatus.CREATED);
    }

    /* find all interviews the interviewer is involved in */
    @GetMapping("/findByInterviewer")
    public ResponseEntity<List<InterviewResponse>> findByInterviewer(){
        return new ResponseEntity<>(interviewService.findByInterviewer(userService.findOne(username)), HttpStatus.OK);
    }

    /* find all interviews the recruiter has organised */
    @GetMapping("/findByRecruiter")
    public ResponseEntity<List<InterviewResponse>> findByRecruiter(){
        return new ResponseEntity<>(interviewService.findByRecruiter(userService.findOne(username)), HttpStatus.OK);
    }
}
