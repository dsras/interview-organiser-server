package com.au.glasgow.serviceImpl;

import com.au.glasgow.entities.Interview;
import com.au.glasgow.repository.InterviewRepository;
import com.au.glasgow.service.ServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterviewService implements ServiceInt<Interview> {

    @Autowired
    private InterviewRepository interviewRepository;

    @Override
    public Interview getById(Integer id) {
        return interviewRepository.getById(id);
    }

    @Override
    public Iterable<Interview> getById(Iterable<Integer> ids) {
        return interviewRepository.findAllById(ids);
    }

    @Override
    public <S extends Interview> Interview save(Interview interview) {
        return interviewRepository.save(interview);
    }

    @Override
    public <S extends Interview> Iterable<S> saveAll(Iterable<S> interviews) {
        return interviewRepository.saveAll(interviews);
    }
}
