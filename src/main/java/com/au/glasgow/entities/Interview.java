package com.au.glasgow.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "interview")
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interview_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "organiser_id", nullable = false)
    private User organiser;

    @Column(name = "interview_date", nullable = false)
    private LocalDate interviewDate;

    @Column(name = "time_start", nullable = false)
    private LocalTime timeStart;

    @Column(name = "time_end", nullable = false)
    private LocalTime timeEnd;

    @Column(name = "status", nullable = true)
    private String status;

    @Column(name = "outcome", nullable = true)
    private String outcome;

    @Column(name = "additional_info",  nullable = true)
    private String info;

    @OneToMany(mappedBy = "interview")
    private Set<InterviewInterviewer> interviewInterviewers = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public User getOrganiser() {
        return organiser;
    }

    public LocalDate getInterviewDate() {
        return interviewDate;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public Interview() {
    }

    public Set<InterviewInterviewer> getInterviewInterviewers() {
        return interviewInterviewers;
    }

    public void setInterviewInterviewers(Set<InterviewInterviewer> interviewInterviewers) {
        this.interviewInterviewers = interviewInterviewers;
    }

    public Interview(User organiser, LocalDate interviewDate, LocalTime timeStart, LocalTime timeEnd, String info) {
        this.organiser = organiser;
        this.interviewDate = interviewDate;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }
}