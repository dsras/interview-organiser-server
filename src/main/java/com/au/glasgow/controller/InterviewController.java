package com.au.glasgow.controller;

import com.au.glasgow.entities.Interview;
import com.au.glasgow.serviceImpl.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interviews")
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    /* test method */
    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome";
    }

    /* get interview by ID
    * NOT WORKING YET
    NEED SERIALIZER FOR DATE AND TIME IN INTERVIEW POJO??
     */
    @GetMapping("/interview")
    @PreAuthorize("hasAnyRole('ADMIN','USER','RECRUITER')")
    public Interview getInterview(@RequestParam(value="id", required = true) Integer id){
        return interviewService.getById(id);
    }

    /* create new interview
    * JSON format for POST request:
    * {
    *   "interviewerId" : 1,
    *   "organiserId" : 2,
    *   "applicantId" : 1,
    *   "roleApplied" : 1,
    *   "interviewDate" : "04-04-2022",
    *   "timeStart" : "09:00",
    *   "timeEnd" : "10:00",
    *   "confirmed" : 0
    * }
    * need to change confirmed to default 0 (tinyint) so we don't need to add it
    * */
    @PostMapping("/new")
    @PreAuthorize("hasAnyRole('ADMIN','RECRUITER')")
    public Interview newInterview(@RequestBody Interview newInterview) {
        return interviewService.save(newInterview);
    }
}
