package com.au.glasgow.entities;

import javax.persistence.*;

@Entity
@Table(name = "interview_applicant")
public class InterviewApplicant {
    @EmbeddedId
    private InterviewApplicantId id;
    @MapsId("interviewId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id")
    private Interview interview;

    @MapsId("appId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "app_id", nullable = false)
    private Applicant app;

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

    public Applicant getApp() {
        return app;
    }

    public void setApp(Applicant app) {
        this.app = app;
    }

    public Interview getInterview() {
        return interview;
    }

    public void setInterview(Interview interview) {
        this.interview = interview;
    }

    public InterviewApplicantId getId() {
        return id;
    }

    public void setId(InterviewApplicantId id) {
        this.id = id;
    }

    //TODO Reverse Engineering! Migrate other columns to the entity
}