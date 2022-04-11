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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organiser_id", nullable = false)
    private User organiser;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "applicant_id", nullable = false)
    private Applicant applicant;

    @Column(name = "interview_date", nullable = false)
    private LocalDate interviewDate;

    @Column(name = "time_start", nullable = false)
    private LocalTime timeStart;

    @Column(name = "time_end", nullable = false)
    private LocalTime timeEnd;

    @Column(name = "confirmed", nullable = false)
    private Integer confirmed;

    @OneToMany(mappedBy = "interview")
    private Set<InterviewInterviewer> interviewInterviewers = new LinkedHashSet<>();

    public Set<InterviewInterviewer> getInterviewInterviewers() {
        return interviewInterviewers;
    }

    public void setInterviewInterviewers(Set<InterviewInterviewer> interviewInterviewers) {
        this.interviewInterviewers = interviewInterviewers;
    }

    public Interview(User organiser, Applicant applicant, LocalDate interviewDate, LocalTime timeStart, LocalTime timeEnd) {
        this.organiser = organiser;
        this.applicant = applicant;
        this.interviewDate = interviewDate;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.confirmed = 0;
    }

    public void confirm(){
        this.confirmed=1;
    }
}