package com.au.glasgow.service;

import com.au.glasgow.entities.Applicant;
import com.au.glasgow.repository.ApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ApplicantService implements ServiceInt<Applicant> {

    @Autowired
    private ApplicantRepository applicantRepository;

    @Override
    public Applicant getById(Integer id) {
        return null;
    }

    @Override
    public Iterable<Applicant> getById(Iterable<Integer> ids) {
        return null;
    }

    @Override
    public <S extends Applicant> Applicant save(Applicant entity) {
        return null;
    }

    @Override
    public <S extends Applicant> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }
}
