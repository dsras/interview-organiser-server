package com.accolite.intervieworganiser.service;

import com.accolite.intervieworganiser.dto.FindInterviewersRequest;
import com.accolite.intervieworganiser.dto.LoginUser;
import com.accolite.intervieworganiser.entities.Role;
import com.accolite.intervieworganiser.entities.User;
import com.accolite.intervieworganiser.entities.UserAvailability;
import com.accolite.intervieworganiser.repository.UserRepository;
import java.util.*;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private RoleService roleService;
    private UserSkillService userSkillService;
    private AvailabilityService availabilityService;

    /**
     * Parameterised constructor.
     *
     * @param userRepository user data access layer
     * @param roleService role service layer
     * @param availabilityService availability service layer
     * @param userSkillService user skill service layer
     */
    public UserService(
        @Autowired UserRepository userRepository,
        @Autowired RoleService roleService,
        @Autowired AvailabilityService availabilityService,
        @Autowired UserSkillService userSkillService
    ) {
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.availabilityService = availabilityService;
        this.userSkillService = userSkillService;
    }

    /* check if user exists */
    public boolean checkIfUserExists(LoginUser loginUser) {
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
        userRepository
            .getRolesByUsername(user.getUsername())
            .forEach(
                role ->
                    authorities.add(
                        new SimpleGrantedAuthority("ROLE_" + role.getName())
                    )
            );
        for (SimpleGrantedAuthority authority : authorities) {
            System.out.println(authority);
        }
        return authorities;
    }

    /* load user by username */
    @Override
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException {
        User user = userRepository.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("invalid username or password");
        }
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            getAuthority(user)
        );
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
    public User getUserDetailsByUsername(String username) {
        return userRepository.getUserDetailsByUsername(username);
    }

    /* get users with required skills available for interview time */
    public List<UserAvailability> getAvailableInterviewers(
        FindInterviewersRequest request
    ) {
        /* get users with required skills */
        List<Integer> skills = request.getSkills();
        List<User> potentialInterviewers = userSkillService.findBySkills(
            skills,
            skills.size()
        );
        /* filter users to those available */
        return availabilityService.getAvailableInterviewers(
            potentialInterviewers,
            request
        );
    }
}
