package com.accolite.intervieworganiser.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * User persistent object.
 * <p>This object stores a user's details and is referenced by other entities ({@link InterviewPanel},
 * {@link UserAvailability}, {@link UserRole}, {@link UserSkill}) to relate users to their availability,
 * interviews, roles and skills. </p>
 */
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, length = 80)
    private String email;

    @Column(name = "mobile")
    private Long mobile;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "business_title", nullable = false, length = 30)
    private String businessTitle;

    @Column(name = "account", length = 225)
    private String account;

    @Column(name = "business_unit")
    private String businessUnit;

    @Column(name = "date_of_joining")
    private LocalDate dateOfJoining;

    @Column(name = "designation")
    private String designation;

    @Column(name = "location")
    private String location;

    @Column(name = "prior_experience")
    private Integer priorExperience;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USER_ROLES",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private List<Role> roles;

    /**
     *
     * @return the user's ID
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @return the user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password the user's password
     */
    public void setPassword(String password) {
        this.password=password;
    }

    /**
     *
     * @return the user's mobile number
     */
    public Long getMobile() {
        return mobile;
    }

    /**
     *
     * @return the user's business title
     */
    public String getBusinessTitle() {
        return businessTitle;
    }

    /**
     *
     * @return the user's account
     */
    public String getAccount() {
        return account;
    }

    /**
     *
     * @return the user's business unit
     */
    public String getBusinessUnit() {
        return businessUnit;
    }

    /**
     *
     * @return the user's date of joining
     */
    public LocalDate getDateOfJoining() {
        return dateOfJoining;
    }

    /**
     *
     * @return the user's designation
     */
    public String getDesignation() {
        return designation;
    }

    /**
     *
     * @return the user's location
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @return the user's number of years of prior experience
     */
    public Integer getPriorExperience() {
        return priorExperience;
    }

    /**
     *
     * @return the user's roles (authorities)
     */
    public List<Role> getRoles(){
        return roles;
    }

    /**
     *
     * @param roles the user's roles
     */
    public void setRoles(List<Role> roles){
        this.roles = roles;
    }

    /**
     *
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Parameterised constructor.
     * <p>Sets the minimum information needed for a user. </p>
     *
     * @param id the user's ID
     * @param username the user's username
     * @param password the user's password
     * @param email the user's email
     * @param name the user's name
     * @param businessTitle the user's business title
     */
    public User(Integer id, String username, String password, String email, String name, String businessTitle) {
        this.id=id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.businessTitle = businessTitle;
    }

    public User(){}
}