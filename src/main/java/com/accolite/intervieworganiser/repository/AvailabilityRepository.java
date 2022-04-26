package com.accolite.intervieworganiser.repository;

import com.accolite.intervieworganiser.entities.User;
import com.accolite.intervieworganiser.entities.UserAvailability;
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

    @Query("SELECT a " +
            "FROM UserAvailability a " +
            "WHERE a.user in :users")
    List<UserAvailability> getByUsers(@Param("users") List<User> users);

    @Query("SELECT a " +
            "FROM UserAvailability a " +
            "WHERE a.availableDate = :date " +
            "AND a.availableFrom <= :startTime " +
            "AND a.availableTo >= :endTime")
    List<UserAvailability> getInTimeInterval(@Param("date") LocalDate date, @Param("startTime") LocalTime
            startTime, @Param("endTime") LocalTime endTime);

    @Query("SELECT a " +
            "FROM UserAvailability a " +
            "WHERE a.user in :users " +
            "AND a.availableDate >= :startDate " +
            "AND a.availableDate <= :endDate " +
            "AND a.availableFrom >= :startTime " +
            "AND a.availableFrom <= :endTime ")
    List<UserAvailability> getAvailableInterviewers(@Param("users") List<User> users, @Param("startDate") LocalDate startDate,
                                @Param("endDate") LocalDate endDate,@Param("startTime") LocalTime startTime,
                                @Param("endTime") LocalTime endTime);

}
