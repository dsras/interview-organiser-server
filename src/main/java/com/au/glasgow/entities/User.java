package com.au.glasgow.entities;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "accolite_user")
public class User {
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USER_ROLES",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private List<Role> roles;

    public Integer getId() { return id;}

    public String getUsername() {return username;}

    public String getUserpassword() {return userpassword;}

    public void setUserpassword(String userpassword) { this.userpassword=userpassword;}

    public List<Role> getRoles(){ return roles;}

    public String getUserName() {
        return userName;
    }

    public void setRoles(List<Role> roles){this.roles = roles;}
}