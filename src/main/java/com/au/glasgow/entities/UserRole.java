package com.au.glasgow.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "USER_ROLES")
public class UserRole {
    @EmbeddedId
    private UserRoleId id;

    public UserRoleId getId() {
        return id;
    }

    public void setId(UserRoleId id) {
        this.id = id;
    }
}