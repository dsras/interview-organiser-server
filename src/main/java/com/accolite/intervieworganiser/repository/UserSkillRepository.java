package com.accolite.intervieworganiser.repository;

import com.accolite.intervieworganiser.entities.Skill;
import com.accolite.intervieworganiser.entities.UserSkill;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserSkillRepository extends JpaRepository<UserSkill, Integer> {
    @Query(
        value = "SELECT u.user_id "
            +
            "FROM user_skills u "
            +
            "WHERE u.skill_id IN (1, 2, 3)"
            +
            "GROUP BY u.user_id; ",
        nativeQuery = true
    )
    List<Integer> findBySkills(
            @Param("skills") List<Integer> skillIds
    );

    @Query("SELECT u.skill FROM UserSkill u WHERE u.user.username = :username")
    List<Skill> findByUser(@Param("username") String username);
}
