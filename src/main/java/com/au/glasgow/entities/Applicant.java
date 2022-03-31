package com.au.glasgow.entities;

import javax.persistence.*;

@Entity
@Table(name = "applicant")
public class Applicant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_id", nullable = false)
    private Integer id;

    @Column(name = "app_firstname", nullable = false)
    private String appFirstname;

    @Column(name = "app_lastame", nullable = false)
    private String appLastame;

    @Column(name = "app_email", nullable = false)
    private String appEmail;

    @Column(name = "app_mobile")
    private Long appMobile;

    public Long getAppMobile() {
        return appMobile;
    }

    public void setAppMobile(Long appMobile) {
        this.appMobile = appMobile;
    }

    public String getAppEmail() {
        return appEmail;
    }

    public void setAppEmail(String appEmail) {
        this.appEmail = appEmail;
    }

    public String getAppLastame() {
        return appLastame;
    }

    public void setAppLastame(String appLastame) {
        this.appLastame = appLastame;
    }

    public String getAppFirstname() {
        return appFirstname;
    }

    public void setAppFirstname(String appFirstname) {
        this.appFirstname = appFirstname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}