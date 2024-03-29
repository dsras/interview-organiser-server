package com.accolite.intervieworganiser.service;

import com.accolite.intervieworganiser.dto.FindInterviewersRequest;
import com.accolite.intervieworganiser.dto.LoginUser;
import com.accolite.intervieworganiser.entities.Role;
import com.accolite.intervieworganiser.entities.User;
import com.accolite.intervieworganiser.dto.UserAvailWithStage;
import com.accolite.intervieworganiser.entities.UserAvailability;
import com.accolite.intervieworganiser.repository.UserRepository;
import java.util.*;

import org.hibernate.engine.transaction.jta.platform.internal.SynchronizationRegistryBasedSynchronizationStrategy;
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

    public List<String> getRoles(User user) {
        List<String> myOut = new ArrayList<>();
        userRepository.getRolesByUsername(user.getUsername())
            .forEach(
                role -> myOut.add(
                    role.getName()
                )
            );
        return myOut;
    }

    /* get permissions for user by retrieving roles from database */
    public Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        userRepository
            .getRolesByUsername(user.getUsername())
            .forEach(
                role -> authorities.add(
                    new SimpleGrantedAuthority("ROLE_" + role.getName())
                )
            );
        for (SimpleGrantedAuthority authority : authorities) {
            System.out.println(authority);
        }
        return authorities;
    }

    public User changeAccountTagTo(String username, String RoundTag) throws UsernameNotFoundException{
        User user = userRepository.getByUsername(username);
        user.setAccount(RoundTag);
        return userRepository.save(user);

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
    public User saveNewUser(User user) {
        user.setPassword(encoder().encode(user.getPassword()));
        Role role = roleService.getByName("USER");
        List<Role> roleSet = new ArrayList<>();
        roleSet.add(role);

        user.setRoles(roleSet);

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
        List<Integer> potentialInterviewers = userSkillService.findBySkills(
            skills
        );
        /* filter users to those available */
        return availabilityService.getAvailableInterviewers(
            potentialInterviewers,
            request
        );
    }

    public List<UserAvailWithStage> getAvailableInterviewersAccurate(
            FindInterviewersRequest request
    ) {
        /* get users with required skills */
        List<Integer> skills = request.getSkills();
        List<Integer> potentialInterviewers = userSkillService.findBySkills(
            skills
        );
        System.out.println("potential ids");
        System.out.println(potentialInterviewers);
        List<String> myTags = new ArrayList<>();

        switch (request.getStage()){
            case "R1":
                myTags = Arrays.asList("R1", "R2", "Sponsor");
                break;
            case "R2":
                myTags = Arrays.asList("R2", "Sponsor");
                break;
            case "Sponsor":
                myTags = Arrays.asList("Sponsor");
                break;
            default:
                break;
        }
        List<UserAvailWithStage> myList = new ArrayList<>();

        for (String tag: myTags ) {
            List<Integer>  newPotentialInterviewers = getUsersInListHavingTag(potentialInterviewers, tag);
            System.out.println("potential ids");
            System.out.println(newPotentialInterviewers);
            /* filter users to those available */
            List<UserAvailability> retData = availabilityService.getAvailableInterviewersAccurate(
                    newPotentialInterviewers,
                    request
            );
            for (UserAvailability retDatum : retData) {
                myList.add(new UserAvailWithStage(retDatum, tag));

            }
        }
        System.out.println("myList");
        for (UserAvailWithStage userAvailWithStage : myList) {
            System.out.println(userAvailWithStage.getStage());
        }
        return myList;
    }

    public List<Integer> getUsersInListHavingTag(List<Integer> inputIds, String tag){
        List<String> tagsList = Arrays.asList(tag);
        return userRepository.getUsersFromListWithTag(inputIds,tagsList);
    }
}
