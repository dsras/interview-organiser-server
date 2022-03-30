package com.au.glasgow.entities;

import javax.persistence.*;

@Entity
@Table(name = "user_skils")
public class UserSkil {
    @EmbeddedId
    private UserSkilId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserSkilId getId() {
        return id;
    }

    public void setId(UserSkilId id) {
        this.id = id;
    }
}