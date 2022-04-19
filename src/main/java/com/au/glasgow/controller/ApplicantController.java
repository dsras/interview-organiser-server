package com.au.glasgow.controller;

import com.au.glasgow.entities.Applicant;
import com.au.glasgow.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /* get all applicants */
//    @PreAuthorize("hasRole('RECRUITER')")
    @GetMapping("/findAll")
    public ResponseEntity<List<Applicant>> getAllApplicants(){
        return new ResponseEntity<>(applicantService.getAllApplicants(), HttpStatus.OK);
    }
}
