package com.au.glasgow.controller;

import com.au.glasgow.entities.Applicant;
import com.au.glasgow.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applicants")
public class ApplicantController {

    @Autowired
    private ApplicantService applicantService;

    //Create new applicant
    @PostMapping("/new")
    public Applicant newApplicant(@RequestBody Applicant newApplicant) {
        return applicantService.save(newApplicant);
    }

    //Get applicant details by email
    @GetMapping("/findApplicant")
    public Applicant getApplicantByEmail(@RequestParam(value="email") String email){
        return applicantService.getApplicantByEmail(email);
    }
}
