package com.au.glasgow.entities;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class InterviewApplicantId implements Serializable {
    private static final long serialVersionUID = 6967576407278894906L;
    @Column(name = "interview_id", nullable = false)
    private Integer interviewId;
    @Column(name = "app_id", nullable = false)
    private Integer appId;
    @Column(name = "skill_id", nullable = false)
    private Integer skillId;

    public Integer getSkillId() {
        return skillId;
    }

    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public Integer getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(Integer interviewId) {
        this.interviewId = interviewId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(skillId, interviewId, appId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        InterviewApplicantId entity = (InterviewApplicantId) o;
        return Objects.equals(this.skillId, entity.skillId) &&
                Objects.equals(this.interviewId, entity.interviewId) &&
                Objects.equals(this.appId, entity.appId);
    }
}