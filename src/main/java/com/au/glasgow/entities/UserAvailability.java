package com.au.glasgow.entities;

import javax.persistence.*;

@Entity
@Table(name = "user_availability")
public class UserAvailability {
    @EmbeddedId
    private UserAvailabilityId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AccoliteUser user;

    public AccoliteUser getUser() {
        return user;
    }

    public void setUser(AccoliteUser user) {
        this.user = user;
    }

    public UserAvailabilityId getId() {
        return id;
    }

    public void setId(UserAvailabilityId id) {
        this.id = id;
    }
}