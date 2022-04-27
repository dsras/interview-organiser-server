package com.accolite.intervieworganiser.service;

import com.accolite.intervieworganiser.dto.FindInterviewersRequest;
import com.accolite.intervieworganiser.dto.LoginUser;
import com.accolite.intervieworganiser.entities.Role;
import com.accolite.intervieworganiser.entities.User;
import com.accolite.intervieworganiser.entities.UserAvailability;
import com.accolite.intervieworganiser.repository.UserRepository;
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

    /**
     * Parameterised constructor.
     * <p>For testing purposes.</p>
     * @param userRepository the UserRepository instance
     * @param roleService the RoleService instance
     * @param availabilityService the AvailabilityService instance
     * @param userSkillService the UserSkillService instance
     */
    public UserService(UserRepository userRepository, RoleService roleService,
                       AvailabilityService availabilityService, UserSkillService userSkillService) {
        this.roleService=roleService;
        this.userRepository=userRepository;
        this.availabilityService=availabilityService;
        this.userSkillService=userSkillService;
    }

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
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                getAuthority(user));
    }

    /* save new user */
    public User save(User user) {
        user.setPassword(encoder().encode(user.getPassword()));
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
    public User getUserDetailsByUsername(String username){
        return userRepository.getUserDetailsByUsername(username);
    }

    public Integer getUserIdByUsername(String username){
        return userRepository.getUserIdByUsername(username);
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
