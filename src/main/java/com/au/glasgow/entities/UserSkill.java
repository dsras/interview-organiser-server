package com.au.glasgow.entities;

import javax.persistence.*;

@Entity
@Table(name = "user_skills")
public class UserSkill {
    @EmbeddedId
    private UserSkillId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AccoliteUser user;

    @MapsId("skillId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public AccoliteUser getUser() {
        return user;
    }

    public void setUser(AccoliteUser user) {
        this.user = user;
    }

    public UserSkillId getId() {
        return id;
    }

    public void setId(UserSkillId id) {
        this.id = id;
    }
}