package com.accolite.intervieworganiser.repository;

import com.accolite.intervieworganiser.entities.Skill;
import com.accolite.intervieworganiser.entities.User;
import com.accolite.intervieworganiser.entities.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserSkillRepository extends JpaRepository<UserSkill, Integer> {

    @Query("SELECT u.user FROM UserSkill u WHERE u.skill.id IN :skills GROUP BY u.user HAVING COUNT(*) = :count")
    List<User> findBySkills(@Param("skills") List<Integer> skillIds, @Param("count") long listSize);

    @Query("SELECT u.skill FROM UserSkill u WHERE u.user.id = :id")
    List<Skill> findByUser(@Param("id") Integer id);
}
