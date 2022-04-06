package com.au.glasgow.repository;

import com.au.glasgow.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    /* custom methods */

    // NOT TESTED YET
    @Query("FROM Role WHERE roleName = :name")
    Role getByName(@Param("name") String name);
}
