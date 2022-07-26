package com.accolite.intervieworganiser.dto;

import com.accolite.intervieworganiser.entities.User;
import com.accolite.intervieworganiser.entities.UserAvailability;
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
public class UserAvailWithStage {

    @JsonProperty("availabilityId")
    private Integer id;

    @JsonProperty("date")
    private LocalDate availableDate;

    @JsonProperty("startTime")
    private LocalTime availableFrom;

    @JsonProperty("endTime")
    private LocalTime availableTo;

    @JsonProperty("interviewer")
    private String interviewer;

    @JsonProperty("interviewerId")
    private Integer interviewerId;

    @JsonProperty("stage")
    private String stage;


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
    public UserAvailWithStage(
            User user,
            LocalDate availableDate,
            LocalTime availableFrom,
            LocalTime availableTo,
            String stage
    ) {
        this.availableDate = availableDate;
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;
        this.interviewer = user.getName();
        this.interviewerId = user.getId();
        this.stage = stage;
    }
    public UserAvailWithStage(UserAvailability avail, String stage){
        this.availableDate = avail.getAvailableDate();
        this.availableFrom = avail.getAvailableFrom();
        this.availableTo = avail.getAvailableTo();
        this.interviewer = avail.getUser().getName();
        this.interviewerId = avail.getUser().getId();
        this.stage = stage;
    }

    public UserAvailWithStage() {
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
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
     * @return the availability ID
     */
    public Integer getId() {
        return id;
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
