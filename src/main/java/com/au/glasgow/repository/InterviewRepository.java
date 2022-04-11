package com.au.glasgow.repository;

import com.au.glasgow.entities.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InterviewRepository extends JpaRepository<Interview, Integer> {

    @Query("UPDATE Interview i SET i.confirmed = 1 WHERE id = :id")
    public Interview confirmInterview(@Param("id") Integer id);
}
