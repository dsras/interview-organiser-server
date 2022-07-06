package com.accolite.intervieworganiser.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.*;

/**
 * User availability persistent object.
 * <p>
 * This object stores information about user availability windows: date, start time, end time and user
 * which references {@link User}.
 * </p>
 */
@Entity
@Table(name = "user_availability")
public class UserAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "availability_id", nullable = false)
    @JsonProperty("availabilityId")
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "available_date", nullable = false)
    @JsonProperty("date")
    private LocalDate availableDate;

    @Column(name = "available_from", nullable = false)
    @JsonProperty("startTime")
    private LocalTime availableFrom;

    @Column(name = "available_to", nullable = false)
    @JsonProperty("endTime")
    private LocalTime availableTo;

    @Transient
    @JsonProperty("interviewer")
    private String interviewer;

    @Transient
    @JsonProperty("interviewerId")
    private Integer interviewerId;

    /**
     * Parameterised constructor.
     * <p>
     * Sets the information needed for an availability window.
     * </p>
     *
     * @param user the user whose availability this is
     * @param availableDate the availability date
     * @param availableFrom the availability start time
     * @param availableTo the availability end time
     */
    public UserAvailability(
            User user,
            LocalDate availableDate,
            LocalTime availableFrom,
            LocalTime availableTo
    ) {
        this.user = user;
        this.availableDate = availableDate;
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;
        this.interviewer = user.getName();
        this.interviewerId = user.getId();
    }

    public UserAvailability() {
    }

    /**
     *
     * @param availableFrom the availability start time
     */
    public void setAvailableFrom(LocalTime availableFrom) {
        this.availableFrom = availableFrom;
    }

    /**
     *
     * @param availableTo the availability end time
     */
    public void setAvailableTo(LocalTime availableTo) {
        this.availableTo = availableTo;
    }

    /**
     *
     * @return the availability end time
     */
    public LocalTime getAvailableTo() {
        return availableTo;
    }

    /**
     *
     * @return the availability start time
     */
    public LocalTime getAvailableFrom() {
        return availableFrom;
    }

    /**
     *
     * @return the availability date
     */
    public LocalDate getAvailableDate() {
        return availableDate;
    }

    /**
     *
     * @return the user whose availability this is
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @return the availability ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Assigns ID and name of user to attributes.
     */
    public void setInterviewer() {
        this.interviewer = user.getName();
        this.interviewerId = user.getId();
    }

    /**
     *
     * @return the interviewer whose availability this is
     */
    public String getInterviewer() {
        return interviewer;
    }

    /**
     *
     * @return the interviewer ID
     */
    public Integer getInterviewerId() {
        return interviewerId;
    }
}
