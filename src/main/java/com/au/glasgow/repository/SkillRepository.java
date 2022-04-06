package com.au.glasgow.repository;

import com.au.glasgow.entities.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Integer> {

    @Query("FROM Skill WHERE skillName = :name")
    List<Skill> getSkillsByName(@Param("name") String name);
}
