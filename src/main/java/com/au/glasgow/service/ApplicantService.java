package com.au.glasgow.service;

import com.au.glasgow.entities.Applicant;
import com.au.glasgow.repository.ApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicantService{

    @Autowired
    private ApplicantRepository applicantRepository;

    /* get Applicant by ID */
    public Applicant getById(Integer applicantId){
        return applicantRepository.getById(applicantId);
    }

    /* update applicant skill */
    public Applicant updateSkill(Integer applicantId, Integer skillId){
        Applicant a = applicantRepository.getById(applicantId);
        a.setSkillId(skillId);
        return applicantRepository.save(a);
    }

    /* save Applicant */
    public Applicant save(Applicant applicant){
        return applicantRepository.save(applicant);
    }

    /* get applicant by email */
    public Applicant getApplicantByEmail(String email) {
        return applicantRepository.getApplicantByEmail(email);
    }
}
