package com.au.glasgow.controller;

import com.au.glasgow.entities.Applicant;
import com.au.glasgow.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applicants")
public class ApplicantController {

    @Autowired
    private ApplicantService applicantService;

    //Create new applicant
    @PostMapping("/new")
    public ResponseEntity<Applicant> newApplicant(@RequestBody Applicant newApplicant) {
        return new ResponseEntity<>(applicantService.save(newApplicant), HttpStatus.CREATED);
    }

    //Get applicant details by email
    @GetMapping("/findApplicant")
    public ResponseEntity<Applicant> getApplicantByEmail(@RequestParam(value = "email") String email) {
        return new ResponseEntity<>(applicantService.getApplicantByEmail(email), HttpStatus.CREATED);
    }

    /* get all applicants */
    //    @PreAuthorize("hasRole('RECRUITER')")
    @GetMapping("/findAll")
    public ResponseEntity<List<Applicant>> getAllApplicants() {
        return new ResponseEntity<>(applicantService.getAllApplicants(), HttpStatus.OK);
    }
}
