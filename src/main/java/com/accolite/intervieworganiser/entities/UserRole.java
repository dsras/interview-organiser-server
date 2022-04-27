package com.accolite.intervieworganiser.entities;

import javax.persistence.*;

/**
 * User role persistent object.
 * <p>This object stores users assigned roles, referencing {@link User} and {@link Role}.</p>
 */
@Entity
@Table(name = "user_roles")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    /**
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     * @return the user-role ID
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id the user-role ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return the role
     */
    public Role getRole() {
        return role;
    }
}