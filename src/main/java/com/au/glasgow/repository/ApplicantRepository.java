package com.au.glasgow.repository;

import com.au.glasgow.entities.Applicant;
import com.au.glasgow.entities.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApplicantRepository extends JpaRepository<Applicant, Integer> {

    @Query("FROM Applicant WHERE email = :email")
    Applicant getApplicantByEmail(@Param("email")String email);

}
