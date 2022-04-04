package com.au.glasgow.repository;

import com.au.glasgow.entities.UserAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailabilityRepository extends JpaRepository<UserAvailability, Integer> {
}
