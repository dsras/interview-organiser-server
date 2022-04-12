package com.au.glasgow.serviceImpl;

import com.au.glasgow.entities.Applicant;
import com.au.glasgow.repository.ApplicantRepository;
import com.au.glasgow.service.ServiceInt;
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
}
