package com.au.glasgow.entities;

import javax.persistence.*;

@Entity
@Table(name = "user_roles")
public class UserRole {
    @EmbeddedId
    private UserRoleId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AccoliteUser user;

    @MapsId("roleId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public AccoliteUser getUser() {
        return user;
    }

    public void setUser(AccoliteUser user) {
        this.user = user;
    }

    public UserRoleId getId() {
        return id;
    }

    public void setId(UserRoleId id) {
        this.id = id;
    }
}