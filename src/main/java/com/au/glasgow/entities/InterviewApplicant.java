package com.au.glasgow.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "interview_applicant")
public class InterviewApplicant {
    @EmbeddedId
    private InterviewApplicantId id;

    public InterviewApplicantId getId() {
        return id;
    }

    public void setId(InterviewApplicantId id) {
        this.id = id;
    }
}