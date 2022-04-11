package com.au.glasgow.controller;

import com.au.glasgow.entities.Interview;
import com.au.glasgow.requestModels.*;
import com.au.glasgow.serviceImpl.InterviewService;
import com.au.glasgow.serviceImpl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /* get interview by ID
    * NOT WORKING YET
    NEED SERIALIZER FOR DATE AND TIME IN INTERVIEW POJO??
     */
    @GetMapping("/interview")
    public Interview getInterview(@RequestParam(value="id") Integer id){
        return interviewService.getById(id);
    }

    @GetMapping("confirm")
    public ResponseEntity<Interview> confirmInterview(@RequestParam(value="id") Integer id){
        return new ResponseEntity<>(interviewService.confirm(id), HttpStatus.OK);
    }

//    /* create new interview */
//    @PostMapping("/new")
//    public ResponseEntity<InterviewResponse> newInterview(@RequestBody InterviewRequest newInterview) {
//        InterviewRequestWrapper wrapper = new InterviewRequestWrapper(newInterview, userService.findOne(username));
//        return new ResponseEntity<>(interviewService.save(wrapper), HttpStatus.CREATED);
//    }
}
