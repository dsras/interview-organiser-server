package com.au.glasgow.repository;

import com.au.glasgow.entities.Interview;
import com.au.glasgow.entities.Skill;
import com.au.glasgow.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface InterviewRepository extends JpaRepository<Interview, Integer> {

    @Query("SELECT i FROM Interview i, InterviewPanel j WHERE i = j.interview and j.interviewer = :user")
    List<Interview> findAllByInterviewer(@Param("user") User user);

    @Query("SELECT i FROM Interview i WHERE i.organiser = :user")
    List<Interview> findAllByRecruiter(@Param("user") User user);

    @Query("SELECT i.interviewer FROM InterviewPanel i WHERE i.interview.id = :id")
    List<User> findInterviewers(@Param("id") Integer id);

    @Query("SELECT COUNT(i) FROM Interview i, InterviewPanel j WHERE i = j.interview " +
            "and i.status = 'Confirmed' and j.interviewer = :user")
    Integer findCompleted(@Param("user") User user);

    @Query("SELECT i FROM Interview i WHERE i.status= :status " +
            "and i.interviewDate <= current_date and i.organiser = :user")
    List<Interview> findStatus(@Param("user") User user, @Param("status") String status);

    @Query("SELECT i FROM Interview i WHERE i.outcome= :outcome " +
            "and i.interviewDate <= current_date " +
            "and i.interviewDate >= :date " +
            "and i.organiser = :user")
    List<Interview> findOutcome(@Param("user") User user, @Param("date") LocalDate date, @Param("outcome") String outcome);

}
