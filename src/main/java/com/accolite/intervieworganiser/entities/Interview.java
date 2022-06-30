package com.accolite.intervieworganiser.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * Interview persistent object.
 * <p>This contains the interview information date, start time, end time, organiser and any additional
 * information provided by the organiser. This is reference by {@link InterviewPanel} objects to allocate
 * interviewers to it.</p>
 */
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

    @Column(name = "additional_info", nullable = true)
    private String info;

    @OneToMany(mappedBy = "interview")
    private Set<InterviewPanel> interviewPanel = new LinkedHashSet<>();

    /**
     * @return the interview id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id the interview ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return the interview organiser
     */
    public User getOrganiser() {
        return organiser;
    }

    /**
     *
     * @return the interview date
     */
    public LocalDate getInterviewDate() {
        return interviewDate;
    }

    /**
     *
     * @return the interview start time
     */
    public LocalTime getTimeStart() {
        return timeStart;
    }

    /**
     *
     * @return the interview end time
     */
    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public Interview() {}

    /**
     *
     * @return set of interview panels for the interview
     */
    public Set<InterviewPanel> getInterviewPanel() {
        return interviewPanel;
    }

    /**
     *
     * @param interviewPanel the set of interview panels for the interview
     */
    public void setInterviewPanel(Set<InterviewPanel> interviewPanel) {
        this.interviewPanel = interviewPanel;
    }

    /**
     * Parameterised constructor.
     * <p>Sets minimum information needed for an interview.</p>
     *
     * @param organiser the interview organiser
     * @param interviewDate the interview date
     * @param timeStart the interview start time
     * @param timeEnd the interview end time
     * @param info additional information about the interview e.g., applicant name and skills being evaluated
     */
    public Interview(
        User organiser,
        LocalDate interviewDate,
        LocalTime timeStart,
        LocalTime timeEnd,
        String info,
        String status,
        String outcome
    ) {
        this.organiser = organiser;
        this.interviewDate = interviewDate;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.info = info;
        this.status = status;
        this.outcome = outcome;
        this.info = info;
    }

    /**
     *
     * @return additional information about the interview as entered by organiser
     */
    public String getInfo() {
        return info;
    }

    /**
     *
     * @return the status of the interview e.g., Candidate No Show
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status the status of the interview e.g., Candidate No Show
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return the outcome of the interview for the candidate e.g., Progressed
     */
    public String getOutcome() {
        return outcome;
    }

    /**
     *
     * @param outcome the outcome of the interview for the candidate e.g., Progressed
     */
    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }
}
