package com.accolite.intervieworganiser.repository;

import com.accolite.intervieworganiser.entities.Skill;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SkillRepository extends JpaRepository<Skill, Integer> {
    @Query("FROM Skill WHERE skillName = :name")
    List<Skill> getSkillsByName(@Param("name") String name);

    @Query("FROM Skill WHERE skillName = :name AND skillLevel = :level")
    Skill getLevel(@Param("name") String name, @Param("level") String level);
}
