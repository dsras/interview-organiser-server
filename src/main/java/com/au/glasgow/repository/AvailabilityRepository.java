package com.au.glasgow.repository;

import com.au.glasgow.entities.UserAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface AvailabilityRepository extends JpaRepository<UserAvailability, Integer> {

    @Query("select a " +
            "from UserAvailability a " +
            "where a.user.username = :username")
    List<UserAvailability> getByUsername(@Param("username") String username);
}
