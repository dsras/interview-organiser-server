package com.au.glasgow.repository;

import com.au.glasgow.entities.Interview;
import com.au.glasgow.entities.Skill;
import com.au.glasgow.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InterviewRepository extends JpaRepository<Interview, Integer> {

    @Query("SELECT i FROM Interview i, InterviewInterviewer j WHERE i = j.interview and j.interviewer = :user")
    List<Interview> findAllByInterviewer(@Param("user") User user);

    @Query("SELECT i FROM Interview i WHERE i.organiser = :user")
    List<Interview> findAllByRecruiter(@Param("user") User user);

    @Query("SELECT i.interviewer FROM InterviewInterviewer i WHERE i.interview.id = :id")
    List<User> findInterviewers(@Param("id") Integer id);

    @Query("SELECT COUNT(i) FROM Interview i, InterviewInterviewer j WHERE i = j.interview and i.status = 'Confirmed' and j.interviewer = :user")
    Integer findConfirmed(@Param("user") User user);

    @Query("SELECT i FROM Interview i WHERE i.status='Candidate No Show' and i.interviewDate <= current_date and i.organiser = :user")
    List<Interview> findUnconfirmed(@Param("user") User user);

}
