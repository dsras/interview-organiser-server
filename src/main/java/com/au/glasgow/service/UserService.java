package com.au.glasgow.service;

import com.au.glasgow.dto.LoginUser;
import com.au.glasgow.entities.Role;
import com.au.glasgow.entities.User;
import com.au.glasgow.repository.UserRepository;
import com.au.glasgow.dto.FindInterviewersRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;


    /* get qualified Interviewers available for Interview */
    public List<User> getAvailableUsers(FindInterviewersRequest findInterviewersRequest){
    /* finds interviewers that
        (1) have these skills
        (2) have availability on this date between these times */
        return userRepository.getAvailableUser(findInterviewersRequest);
    }

    /* check if user exists */
    public boolean checkIfUserExists(LoginUser loginUser){
        User user = findOne(loginUser.getUsername());
        return null != user;
    }

    /* get user by username */
    public User findOne(String username) {
        return userRepository.getByUsername(username);
    }

    /* get user by id */
    public User getById(Integer id) {
        return userRepository.getById(id);
    }

    /* find user by email */
    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    /* get permissions for user by retrieving roles from database*/
    public Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        userRepository.getRolesByUsername(user.getUsername()).forEach(
                role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));
        for (SimpleGrantedAuthority authority : authorities) {
            System.out.println(authority);
        }
        return authorities;
    }

    /* load user by username */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getUserpassword(),
                getAuthority(user));
    }

    /* save new user */
    public User save(User user) {
        User nUser = new User();
        BeanUtils.copyProperties(user, nUser);
        nUser.setUserpassword(encoder().encode(user.getUserpassword()));
        Role role = roleService.getByName("USER");
        List<Role> roleSet = new ArrayList<>();
        roleSet.add(role);
        if (user.getRoles() != null) {
            for (Role roles : user.getRoles()) {
                roleSet.add(roleService.getByName(roles.getName()));
            }
            nUser.setRoles(roleSet);
        }
        return userRepository.save(nUser);
    }

    /* password encoder */
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /* get user details by username */
    public User getUserDetailsByUsername(String username){
        return userRepository.getUserDetailsByUsername(username);
    }
}
