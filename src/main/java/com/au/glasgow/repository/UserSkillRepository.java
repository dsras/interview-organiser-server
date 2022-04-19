package com.au.glasgow.repository;

import com.au.glasgow.entities.Skill;
import com.au.glasgow.entities.User;
import com.au.glasgow.entities.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface UserSkillRepository extends JpaRepository<UserSkill, Integer> {

    @Query("select s " +
            "from Skill s " +
            "where s in (select u.skill from UserSkill u where u.user.id = :id)")
    Set<Skill> getUserSkills(@Param("id") Integer id);


    @Query("SELECT u.user FROM UserSkill u WHERE u.skill.id IN :skills GROUP BY u.user HAVING COUNT(*) = :count")
    List<User> findBySkills(@Param("skills") List<Integer> skillIds, @Param("count") long listSize);
}
