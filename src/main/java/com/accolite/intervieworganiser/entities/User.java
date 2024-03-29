package com.accolite.intervieworganiser.entities;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * User persistent object.
 * <p>
 * This object stores a user's details and is referenced by other entities ({@link InterviewPanel},
 * {@link UserAvailability}, {@link UserRole}, {@link UserSkill}) to relate users to their availability,
 * interviews, roles and skills.
 * </p>
 */
@Entity
@Table(name = "accolite_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Column(name = "username", nullable = false)
    @NotEmpty(message = "Please provide username")
    private String username;

    @Column(name = "password", nullable = false)
    @NotEmpty(message = "Please provide password")
    private String password;

    @Column(name = "email", nullable = false, length = 80)
    @NotEmpty(message = "Please provide email")
    @Email(message = "Please provide a valid email")
    private String email;

    @Column(name = "mobile")
    private Long mobile;

    @Column(name = "name", nullable = false, length = 30)
    @NotEmpty(message = "Please provide name")
    private String name;

    @Column(name = "business_title", nullable = false, length = 30)
    @NotEmpty(message = "Please provide business title")
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
    @JoinTable(
        name = "USER_ROLES",
        joinColumns = @JoinColumn(name = "USER_ID"),
        inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
    )
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
     * @param id the user's ID
     */
    public void setId(Integer id) {
        this.id = id;
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
        this.password = password;
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
    public List<Role> getRoles() {
        return roles;
    }

    /**
     *
     * @param roles the user's roles
     */
    public void setRoles(List<Role> roles) {
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
     *
     * @return the user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Parameterised constructor.
     * <p>
     * Sets the minimum information needed for a user.
     * </p>
     *
     * @param username the user's username
     * @param password the user's password
     * @param email the user's email
     * @param name the user's name
     * @param businessTitle the user's business title
     */
    public User(
            String username,
            String password,
            String email,
            String name,
            String businessTitle
    ) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.businessTitle = businessTitle;
    }

    public User(){

    }
    public User(User Updated) {
        this.setAccount(Updated.getAccount());
        this.setRoles(Updated.getRoles());
        this.setBusinessTitle(Updated.getBusinessTitle());
        this.setDesignation(Updated.getDesignation());
        this.setEmail(Updated.getEmail());
        this.setId(Updated.getId());
        this.setDateOfJoining(Updated.getDateOfJoining());
        this.setBusinessUnit(Updated.getBusinessUnit());
        this.setMobile(Updated.getMobile());
        this.setLocation(Updated.getLocation());
        this.setName(Updated.getName());
        this.setPassword(Updated.getPassword());
        this.setPriorExperience(Updated.getPriorExperience());
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBusinessTitle(String businessTitle) {
        this.businessTitle = businessTitle;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public void setDateOfJoining(LocalDate dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPriorExperience(Integer priorExperience) {
        this.priorExperience = priorExperience;
    }
}
