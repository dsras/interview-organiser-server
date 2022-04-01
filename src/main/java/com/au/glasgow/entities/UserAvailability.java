package com.au.glasgow.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_availability")
public class UserAvailability {
    @EmbeddedId
    private UserAvailabilityId id;

    public UserAvailabilityId getId() {
        return id;
    }

    public void setId(UserAvailabilityId id) {
        this.id = id;
    }
}