package com.au.glasgow.entities;

import com.au.glasgow.utility.DateHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organiser_id", nullable = false)
    private User organiser;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "interviewer_id", nullable = false)
    private User interviewer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "applicant_id", nullable = false)
    private Applicant applicant;

    @Column(name = "role_applied", nullable = false)
    private String roleApplied;

    @Column(name = "interview_date", nullable = false)
    @JsonFormat(pattern="dd-MM-yyyy")
//    @JsonDeserialize(using = DateHandler.class)
    private LocalDate interviewDate;

    @Column(name = "time_start", nullable = false)
    @JsonFormat(pattern = "HH:mm")
    private LocalTime timeStart;

    @Column(name = "time_end", nullable = false)
    @JsonFormat(pattern = "HH:mm")
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

    public String getRoleApplied() {
        return roleApplied;
    }

    public void setRoleApplied(String roleApplied) {
        this.roleApplied = roleApplied;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public User getInterviewer() {
        return interviewer;
    }

    public void setInterviewer(User interviewer) {
        this.interviewer = interviewer;
    }

    public User getOrganiser() {
        return organiser;
    }

    public void setOrganiser(User organiser) {
        this.organiser = organiser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}