package com.au.glasgow.entities;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Embeddable
public class UserAvailabilityId implements Serializable {
    private static final long serialVersionUID = 5091416534027691520L;
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @Column(name = "availabile_date", nullable = false)
    private LocalDate availabileDate;
    @Column(name = "available_from", nullable = false)
    private LocalTime availableFrom;
    @Column(name = "available_to", nullable = false)
    private LocalTime availableTo;

    public LocalTime getAvailableTo() {
        return availableTo;
    }

    public void setAvailableTo(LocalTime availableTo) {
        this.availableTo = availableTo;
    }

    public LocalTime getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(LocalTime availableFrom) {
        this.availableFrom = availableFrom;
    }

    public LocalDate getAvailabileDate() {
        return availabileDate;
    }

    public void setAvailabileDate(LocalDate availabileDate) {
        this.availabileDate = availabileDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(availabileDate, userId, availableFrom, availableTo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserAvailabilityId entity = (UserAvailabilityId) o;
        return Objects.equals(this.availabileDate, entity.availabileDate) &&
                Objects.equals(this.userId, entity.userId) &&
                Objects.equals(this.availableFrom, entity.availableFrom) &&
                Objects.equals(this.availableTo, entity.availableTo);
    }
}