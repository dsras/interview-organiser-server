package com.accolite.intervieworganiser.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "user_availability")
public class UserAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "availability_id", nullable = false)
    @JsonProperty("availability_id")
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "available_date", nullable = false)
    @JsonProperty("date")
    private LocalDate availableDate;

    @Column(name = "available_from", nullable = false)
    @JsonProperty("start_time")
    private LocalTime availableFrom;

    @Column(name = "available_to", nullable = false)
    @JsonProperty("end_time")
    private LocalTime availableTo;

    @Transient
    @JsonProperty("interviewer")
    private String interviewer;

    @Transient
    @JsonProperty("interviewer_id")
    private Integer interviewerId;

    public UserAvailability(User user, LocalDate availableDate, LocalTime availableFrom, LocalTime availableTo){
        this.user=user;
        this.availableDate=availableDate;
        this.availableFrom=availableFrom;
        this.availableTo=availableTo;
        this.interviewer=user.getUserName();
        this.interviewerId= user.getId();
    }

    public UserAvailability(){}

    public void setAvailableFrom(LocalTime availableFrom) {
        this.availableFrom = availableFrom;
    }

    public void setAvailableTo(LocalTime availableTo) {
        this.availableTo = availableTo;
    }

    public LocalTime getAvailableTo() {
        return availableTo;
    }

    public LocalTime getAvailableFrom() {
        return availableFrom;
    }

    public LocalDate getAvailableDate() {
        return availableDate;
    }

    public User getUser() {
        return user;
    }

    public Integer getId() {
        return id;
    }

    public void setInterviewer(){
        this.interviewer = user.getUserName();
        this.interviewerId= user.getId();
    }


}