package com.au.glasgow.repository;

import com.au.glasgow.entities.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantRepository extends JpaRepository<Applicant, Integer> {
}
