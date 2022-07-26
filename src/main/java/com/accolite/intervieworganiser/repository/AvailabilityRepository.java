package com.accolite.intervieworganiser.repository;

import com.accolite.intervieworganiser.dto.UserAvailWithStage;
import com.accolite.intervieworganiser.entities.UserAvailability;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface AvailabilityRepository
        extends
            JpaRepository<UserAvailability, Integer> {

    @Transactional
    @Modifying
    @Query(
        "DELETE "
            +
            "FROM UserAvailability a "
            +
            "WHERE a.id = :availId"
    )
    void deleteAvailability(@Param("availId") Integer availabilityId);

    @Query(
        "SELECT a "
            +
            "FROM UserAvailability a "
            +
            "WHERE a.user.username = :username "
            +
            "AND a.availableDate >= :startDate "
            +
            "AND a.availableDate <= :endDate "
    )
    List<UserAvailability> getByRange(
            @Param("username") String username,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query(
        "SELECT a "
            +
            "FROM UserAvailability a "
            +
            "WHERE a.user.username != ''"
            +
            "AND a.availableDate >= :startDate "
            +
            "AND a.availableDate <= :endDate "
    )
    List<UserAvailability> getByRangeRec(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query(
        "SELECT a "
            +
            "FROM UserAvailability a "
            +
            "WHERE a.user.username = :username"
    )
    List<UserAvailability> getByUsername(@Param("username") String username);

    @Query("SELECT a " + "FROM UserAvailability a " + "WHERE a.user in :users")
    List<UserAvailability> getByUsers(@Param("users") List<Integer> users);

    @Query(
        "SELECT a "
            +
            "FROM UserAvailability a "
            +
            "WHERE a.availableDate = :date "
            +
            "AND a.availableFrom <= :startTime "
            +
            "AND a.availableTo >= :endTime"
    )
    List<UserAvailability> getInTimeInterval(
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );

    @Query(
            "SELECT a "
                    +
                    "FROM UserAvailability a "
                    +
                    "WHERE a.availableDate = :date "
                    +
                    "AND a.availableFrom <= :startTime "
                    +
                    "AND a.availableTo >= :endTime "
                    +
                    "AND a.user.id in :id"
    )
    List<UserAvailability> getInTimeIntervalWithId(
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime,
            @Param("id") List<Integer> id
    );

    @Query(
            "SELECT " +
                    "a " +
                    "FROM " +
                    "UserAvailability a " +
                    "WHERE " +
                        "a.availableDate = :date " +
                        "AND ( " +
                        "( " +
                            "a.availableTo > :startTime " +
                            "and a.availableFrom < :startTime " +
                        ") " +
                        "or ( " +
                            "a.availableTo > :endTime " +
                            "and a.availableFrom < :endTime " +
                        ") " +
                        "or ( " +
                            "a.availableFrom = :startTime " +
                            "or a.availableFrom = :endTime " +
                            "or a.availableTo = :startTime " +
                            "or a.availableTo = :endTime " +
                        ") " +
                        "or ( " +
                            "a.availableFrom > :startTime " +
                            "and a.availableTo < :endTime " +
                        ") " +
                        "or ( " +
                            "a.availableFrom < :startTime " +
                            "and a.availableTo > :endTime " +
                        ") " +
                    ") " +
                    "and a.user.id in :id "
    )
    List<UserAvailability> getAdjacentAvailability(
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime,
            @Param("id") List<Integer> id
    );

    @Transactional
    @Modifying
    @Query(
            value = "Update user_availability "+
                    "Set available_from = :startTime "+
                    "Where availability_id = :id ;",
            nativeQuery = true
    )
    void updateStartTime(
            @Param("id") Integer id,
            @Param("startTime") LocalTime startTime
    );
    @Transactional
    @Modifying
    @Query(
            value = "Update user_availability "+
                    "Set available_to = :endTime "+
                    "Where availability_id = :id ;",
            nativeQuery = true
    )
    void updateEndTime(
            @Param("id") Integer id,
            @Param("endTime") LocalTime startTime
    );

    @Query(
        "SELECT a "
            +
            "FROM UserAvailability a "
            +
            "WHERE a.user.id in :users "
            +
            "AND a.availableDate >= :startDate "
            +
            "AND a.availableDate <= :endDate "
            +
            "AND a.availableFrom >= :startTime "
            +
            "AND a.availableFrom <= :endTime "
    )
    List<UserAvailability> getAvailableInterviewers(
            @Param("users") List<Integer> users,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );

    @Query(
        value = "Select "
            +
            "myData.id as availability_id, "
            +
            "myData.available_date as available_date, "
            +
            "myData.user_id as user_id, "
            +
            "myData.startTime as available_from, "
            +
            "myData.endTime as available_to "
            +
            "from( "
            +
            "Select "
            +
            "If(ua.available_from > :startTime, ua.available_from, :startTime) as startTime, "
            +
            "If(ua.available_to < :endTime, ua.available_to, :endTime) as endTime, "
            +
            "ua.availability_id as id, "
            +
            "ua.user_id, "
            +
            "ua.available_date "
            +
            "from ( "
            +
            "Select * "
            +
            "from "
            +
            "user_availability as u "
            +
            "where u.available_date >= :startDate "
            +
            "and u.available_date <= :endDate "
            +
            ") as ua "
            +
            ") as myData "
            +
            "where myData.user_id in :users "
            +
            "and myData.startTime != myData.endTime "
            +
            "and myData.startTime < myData.endTime "
            +
            "and myData.endTime > myData.startTime; ",
        nativeQuery = true
    )
    List<UserAvailability> getAvailableInterviewersAccurate(
            @Param("users") List<Integer> users,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );
}
