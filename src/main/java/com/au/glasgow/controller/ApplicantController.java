package com.au.glasgow.controller;

import com.au.glasgow.entities.Applicant;
import com.au.glasgow.serviceImpl.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applicants")
public class ApplicantController {

    @Autowired
    private ApplicantService applicantService;

    /* test method */
    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome";
    }

    /* create new user
    * JSON format for POST request body:
    * {
    *   "firstName" : "Anna",
    *   "lastName" : "Brown",
    *   "email" : "ab@gmail.com"
    * }
    * */
    @PostMapping("/new")
    public Applicant newApplicant(@RequestBody Applicant applicant) {
        return applicantService.save(applicant);
    }
}
