package com.accolite.intervieworganiser.entities;

import javax.persistence.*;

/**
 * Interview panel persistent object.
 * <p>
 * This contains one interviewer as a member of a panel which may consist of one or more interviewers.
 * To form a panel multiple interview panel objects are saved with the same interview ID, referencing the
 * {@link Interview} the panel is for. The ID of this object refers to the combination of the interview and ONE
 * interviewer - each interviewer will generate a new interview panel ID referencing this combination.
 * </p>
 */
@Entity
@Table(name = "interview_panel")
public class InterviewPanel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "interview_id", nullable = false)
    private Interview interview;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "interviewer_id", nullable = false)
    private User interviewer;

    /**
     *
     * @return the interview who is a member of the interview panel
     */
    public User getInterviewer() {
        return interviewer;
    }

    /**
     *
     * @return the interview the panel is for
     */
    public Interview getInterview() {
        return interview;
    }

    /**
     *
     * @return the interviewer ID as panel member (this differs from their user ID)
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id the interview ID as panel member (this differs from their user ID)
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public InterviewPanel() {
    }

    /**
     *
     * @param interview the interview the panel is for
     * @param interviewer the interviewer being added to the panel
     */
    public InterviewPanel(Interview interview, User interviewer) {
        this.interview = interview;
        this.interviewer = interviewer;
    }
}
