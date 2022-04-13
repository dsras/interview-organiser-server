package com.au.glasgow.repository;

import com.au.glasgow.entities.UserAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AvailabilityRepository extends JpaRepository<UserAvailability, Integer> {

    @Query("SELECT a " +
            "FROM UserAvailability a " +
            "WHERE a.user.username = :username")
    List<UserAvailability> getByUsername(@Param("username") String username);

    @Query("SELECT a FROM UserAvailability a WHERE a.availableDate = :date " +
            "AND a.availableFrom <= :startTime AND a.availableTo >= :endTime")
    List<UserAvailability> getInTimeInterval(@Param("date") LocalDate date, @Param("startTime") LocalTime
            startTime, @Param("endTime") LocalTime endTime);

//    List<UserAvailability> findBySkill(@Param("skill") Integer skillId);
}
