package com.au.glasgow.entities;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "accolite_user")
public class AccoliteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "userpassword", nullable = false)
    private String userpassword;

    @Column(name = "user_email", nullable = false, length = 80)
    private String userEmail;

    @Column(name = "user_mobile")
    private Long userMobile;

    @Column(name = "user_name", nullable = false, length = 30)
    private String userName;

    @Column(name = "business_title", nullable = false, length = 30)
    private String businessTitle;

    @Column(name = "account", length = 225)
    private String account;

    @Column(name = "business_unit")
    private String businessUnit;

    @Column(name = "date_of_joining")
    private Instant dateOfJoining;

    @Column(name = "designation")
    private String designation;

    @Column(name = "location")
    private String location;

    @Column(name = "prior_experience")
    private Integer priorExperience;

    public Integer getPriorExperience() {
        return priorExperience;
    }

    public void setPriorExperience(Integer priorExperience) {
        this.priorExperience = priorExperience;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Instant getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(Instant dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBusinessTitle() {
        return businessTitle;
    }

    public void setBusinessTitle(String businessTitle) {
        this.businessTitle = businessTitle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(Long userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}