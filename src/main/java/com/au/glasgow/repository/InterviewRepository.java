package com.au.glasgow.repository;

import com.au.glasgow.entities.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewRepository extends JpaRepository<Interview, Integer> {
}
