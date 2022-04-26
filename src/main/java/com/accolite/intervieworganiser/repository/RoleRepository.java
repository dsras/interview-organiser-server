package com.accolite.intervieworganiser.repository;


import com.accolite.intervieworganiser.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("FROM Role WHERE name = :name")
    Role getByName(@Param("name") String name);
}
