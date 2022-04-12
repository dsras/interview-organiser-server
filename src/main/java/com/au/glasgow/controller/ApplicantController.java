package com.au.glasgow.controller;

import com.au.glasgow.entities.Applicant;
import com.au.glasgow.serviceImpl.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applicants")
public class ApplicantController {

    @Autowired
    private ApplicantService applicantService;

    /* create new Applicant */
    @PostMapping("/new")
    public Applicant newApplicant(@RequestBody Applicant applicant) {
        return applicantService.save(applicant);
    }
}
