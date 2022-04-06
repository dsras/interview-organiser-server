package com.au.glasgow.dto;

import com.au.glasgow.entities.Role;
import lombok.Data;
import java.util.Set;

@Data
public class UserResponse {

    private String username;

    private String userpassword;

    private String userEmail;

    private Long userMobile;

    private String userName;

    private String businessTitle;

    private Set<Role> roles;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return userEmail;
    }

    public Long getMobile() {
        return userMobile;
    }

    public String getName() {
        return userName;
    }

    public String getBusinessTitle() {
        return businessTitle;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getPassword() {
        return userpassword;
    }
}
