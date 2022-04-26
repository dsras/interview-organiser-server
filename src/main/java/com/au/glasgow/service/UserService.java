package com.au.glasgow.service;

import com.au.glasgow.dto.LoginUser;
import com.au.glasgow.entities.Role;
import com.au.glasgow.entities.User;
import com.au.glasgow.entities.UserAvailability;
import com.au.glasgow.repository.UserRepository;
import com.au.glasgow.dto.FindInterviewersRequest;
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

    @Autowired
    private UserSkillService userSkillService;

    @Autowired
    private AvailabilityService availabilityService;

    public UserService(UserRepository repository, RoleService roleService, AvailabilityService availabilityService,
                       UserSkillService userSkillService) {
        this.userRepository=repository;
        this.roleService=roleService;
        this.availabilityService=availabilityService;
        this.userSkillService=userSkillService;
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

    /* get permissions for user by retrieving roles from database */
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
        user.setUserpassword(encoder().encode(user.getUserpassword()));
        Role role = roleService.getByName("USER");
        List<Role> roleSet = new ArrayList<>();
        roleSet.add(role);
        if (user.getRoles() != null) {
            for (Role roles : user.getRoles()) {
                roleSet.add(roleService.getByName(roles.getName()));
            }
            user.setRoles(roleSet);
        }
        return userRepository.save(user);
    }

    /* password encoder */
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /* get user details by username */
    public User getByUsername(String username){
        return userRepository.getByUsername(username);
    }

    /* get users with required skills available for interview time */
    public List<UserAvailability> getAvailableInterviewers(FindInterviewersRequest request){
        /* get users with required skills */
        List<Integer> skills = request.getSkills();
        List<User> potentialInterviewers = userSkillService.findBySkills(skills, skills.size());
        /* filter users to those available */
        return availabilityService.getAvailableInterviewers(potentialInterviewers, request);
    }
}
