package com.au.glasgow.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "user_availability")
public class UserAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "availability_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "available_date", nullable = false)
    private LocalDate availableDate;

    @Column(name = "available_from", nullable = false)
    private LocalTime availableFrom;

    @Column(name = "available_to", nullable = false)
    private LocalTime availableTo;

    public UserAvailability(User user, LocalDate availableDate, LocalTime availableFrom, LocalTime availableTo){
        this.user=user;
        this.availableDate=availableDate;
        this.availableFrom=availableFrom;
        this.availableTo=availableTo;
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

}