package com.au.glasgow.entities;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserSkilId implements Serializable {
    private static final long serialVersionUID = 1323927123179004918L;
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @Column(name = "skill_id", nullable = false)
    private Integer skillId;

    public Integer getSkillId() {
        return skillId;
    }

    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(skillId, userId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserSkilId entity = (UserSkilId) o;
        return Objects.equals(this.skillId, entity.skillId) &&
                Objects.equals(this.userId, entity.userId);
    }
}