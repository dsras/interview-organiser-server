package com.au.glasgow.entities;

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
}