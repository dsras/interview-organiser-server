package com.accolite.intervieworganiser.entities;

import javax.persistence.*;

@Entity
@Table(name = "skill")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id", nullable = false)
    private Integer id;

    @Column(name = "skill_name", nullable = false, length = 30)
    private String skillName;

    @Column(name = "skill_level", nullable = false, length = 30)
    private String skillLevel;

    public Integer getId() {
        return id;
    }

    public String getSkillName() {
        return skillName;
    }

    public String getSkillLevel() {
        return skillLevel;
    }
}