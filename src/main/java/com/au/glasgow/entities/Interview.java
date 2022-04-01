package com.au.glasgow.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "interview")
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interview_id", nullable = false)
    private Integer id;

    @Column(name = "organiser_id", nullable = false)
    private Integer organiserId;

    @Column(name = "interviewer_id", nullable = false)
    private Integer interviewerId;

    @Column(name = "applicant_id", nullable = false)
    private Integer applicantId;

    @Column(name = "role_applied", nullable = false)
    private Integer roleApplied;

    @Column(name = "interview_date", nullable = false)
    private LocalDate interviewDate;

    @Column(name = "time_start", nullable = false)
    private LocalTime timeStart;

    @Column(name = "time_end", nullable = false)
    private LocalTime timeEnd;

    @Column(name = "confirmed", nullable = false)
    private Integer confirmed;

    public Integer getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Integer confirmed) {
        this.confirmed = confirmed;
    }

    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    public LocalDate getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(LocalDate interviewDate) {
        this.interviewDate = interviewDate;
    }

    public Integer getRoleApplied() {
        return roleApplied;
    }

    public void setRoleApplied(Integer roleApplied) {
        this.roleApplied = roleApplied;
    }

    public Integer getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Integer applicantId) {
        this.applicantId = applicantId;
    }

    public Integer getInterviewerId() {
        return interviewerId;
    }

    public void setInterviewerId(Integer interviewerId) {
        this.interviewerId = interviewerId;
    }

    public Integer getOrganiserId() {
        return organiserId;
    }

    public void setOrganiserId(Integer organiserId) {
        this.organiserId = organiserId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}