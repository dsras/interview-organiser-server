package com.au.glasgow.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "interview")
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interview_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organiser_id", nullable = false)
    private AccoliteUser organiser;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "interviewer_id", nullable = false)
    private AccoliteUser interviewer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "applicant_id", nullable = false)
    private Applicant applicant;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_applied", nullable = false)
    private Role roleApplied;

    @Column(name = "interview_date", nullable = false)
    private LocalDate interviewDate;

    @Column(name = "time_start", nullable = false)
    private LocalTime timeStart;

    @Column(name = "time_end", nullable = false)
    private LocalTime timeEnd;

    @Column(name = "confirmed", nullable = false)
    private Integer confirmed;

    @OneToMany(mappedBy = "interview")
    private Set<InterviewApplicant> interviewApplicants = new LinkedHashSet<>();

    public Set<InterviewApplicant> getInterviewApplicants() {
        return interviewApplicants;
    }

    public void setInterviewApplicants(Set<InterviewApplicant> interviewApplicants) {
        this.interviewApplicants = interviewApplicants;
    }

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

    public Role getRoleApplied() {
        return roleApplied;
    }

    public void setRoleApplied(Role roleApplied) {
        this.roleApplied = roleApplied;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public AccoliteUser getInterviewer() {
        return interviewer;
    }

    public void setInterviewer(AccoliteUser interviewer) {
        this.interviewer = interviewer;
    }

    public AccoliteUser getOrganiser() {
        return organiser;
    }

    public void setOrganiser(AccoliteUser organiser) {
        this.organiser = organiser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}