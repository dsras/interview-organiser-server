package com.accolite.intervieworganiser.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * Skill persistent object.
 * <p>This object stores skill name and level e.g., Java Expert. This is referenced by {@link User}
 * to store a user's skills.</p>
 */
@Entity
@Table(name = "skill")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id", nullable = false)
    private Integer id;

    @Column(name = "skill_name", nullable = false, length = 30)
    @NotEmpty(message = "Please provide skill name")
    private String skillName;

    @Column(name = "skill_level", nullable = false, length = 30)
    @NotEmpty(message = "Please provide skill level")
    private String skillLevel;

    public Skill(String skillName, String skillLevel) {
        this.skillName = skillName;
        this.skillLevel = skillLevel;
    }

    public Skill(){}

    /**
     *
     * @return the skill ID
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @return the skill name
     */
    public String getSkillName() {
        return skillName;
    }

    /**
     *
     * @return the skill level
     */
    public String getSkillLevel() {
        return skillLevel;
    }

    public Skill(){}
    public Skill(String skillName, String skillLevel){
        this.skillName=skillName;
        this.skillLevel=skillLevel;
    }
}