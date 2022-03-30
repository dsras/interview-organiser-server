package com.au.glasgow.entities;

import javax.persistence.*;

@Entity
@Table(name = "interview_applicant")
public class InterviewApplicant {
    @Id
    @Column(name = "interview_id", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "interview_id", nullable = false)
    private Interview interview;

    @Column(name = "skill", nullable = false, length = 30)
    private String skill;

    @Column(name = "level", nullable = false, length = 30)
    private String level;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public Interview getInterview() {
        return interview;
    }

    public void setInterview(Interview interview) {
        this.interview = interview;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}