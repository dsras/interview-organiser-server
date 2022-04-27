package com.accolite.intervieworganiser.entities;

import javax.persistence.*;

/**
 * User skill persistent object.
 * <p>This object stores users assigned skills, referencing {@link Skill}, {@link User}.</p>
 */
@Entity
@Table(name = "user_skills")
public class UserSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userskill_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    public UserSkill(){}
    public UserSkill(User user, Skill skill){
        this.user=user;
        this.skill=skill;
    }

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
     * @return the user-skill ID
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id the user-skill ID
     */
    public void setId(Integer id) {
        this.id = id;
    }
}