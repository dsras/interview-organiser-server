package com.au.glasgow.serviceImpl;

import com.au.glasgow.entities.Applicant;
import com.au.glasgow.repository.ApplicantRepository;
import com.au.glasgow.service.ServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicantService implements ServiceInt<Applicant> {

    @Autowired
    private ApplicantRepository applicantRepository;

    @Override
    public Applicant getById(Integer id) {
        return applicantRepository.getById(id);
    }

    @Override
    public Iterable<Applicant> getById(Iterable<Integer> ids) {
        return applicantRepository.findAllById(ids);
    }

    @Override
    public <S extends Applicant> Applicant save(Applicant applicant) {
        return applicantRepository.save(applicant);
    }

    @Override
    public <S extends Applicant> Iterable<S> saveAll(Iterable<S> applicants) {
        return applicantRepository.saveAll(applicants);
    }
}
