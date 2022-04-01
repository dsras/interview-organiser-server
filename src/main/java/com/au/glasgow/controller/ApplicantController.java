package com.au.glasgow.controller;

import com.au.glasgow.entities.Applicant;
import com.au.glasgow.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


//    /* get user by <some field>  - CHANGE USERNAME TO SOME OTHER FIELD */
//    @GetMapping("/applicant")
//    public ResponseEntity<Applicant> getApplicant(@RequestParam(value="username", required = true) String username){
//        return new ResponseEntity<Applicant>(applicantService.getByUsername(username), HttpStatus.OK);
//    }

    /* create new user */
    @PostMapping("/new")
    public ResponseEntity<Applicant> newApplicant(@RequestBody Applicant applicant) {
        return new ResponseEntity<Applicant>(applicantService.save(applicant), HttpStatus.CREATED);
    }
}
