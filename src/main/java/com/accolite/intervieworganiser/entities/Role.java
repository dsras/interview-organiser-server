package com.accolite.intervieworganiser.entities;

import javax.persistence.*;
import java.util.Set;

/**
 * Role persistent object.
 * <p>This contains the authority of a user and is used to decide user permissions.</p>
 */
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
    private Integer id;

    @Column(name = "description", nullable = false, length = 300)
    private String description;

    @Column(name = "role_name", nullable = false, length = 100)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    private Set<User> users;

    /**
     *
     * @return the role name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param roleName the role name
     */
    public void setName(String roleName) {
        this.name = roleName;
    }

    /**
     *
     * @return the role ID
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id the role ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return the role description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description the role description
     */
    public void setDescription(String description) {
        this.description=description;
    }
}