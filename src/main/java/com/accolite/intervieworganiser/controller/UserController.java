package com.accolite.intervieworganiser.controller;

import com.accolite.intervieworganiser.config.TokenProvider;
import com.accolite.intervieworganiser.dto.AuthToken;
import com.accolite.intervieworganiser.dto.FindInterviewersRequest;
import com.accolite.intervieworganiser.dto.LoginUser;
import com.accolite.intervieworganiser.entities.Skill;
import com.accolite.intervieworganiser.entities.User;
import com.accolite.intervieworganiser.entities.UserAvailability;
import com.accolite.intervieworganiser.entities.UserSkill;
import com.accolite.intervieworganiser.exception.InvalidTokenException;
import com.accolite.intervieworganiser.repository.UserSkillRepository;
import com.accolite.intervieworganiser.service.SkillService;
import com.accolite.intervieworganiser.service.TokenValidationService;
import com.accolite.intervieworganiser.service.UserService;
import com.accolite.intervieworganiser.service.UserSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/*
handles user-related requests
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private TokenValidationService tokenValidationService;

    @Autowired
    private UserService userService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private UserSkillService userSkillService;

    @Autowired
    private UserSkillRepository userSkillRepository;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> generateToken(@RequestBody LoginUser loginUser) throws AuthenticationException {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@accolitedigital.com";
        if (Pattern.compile(regex).matcher(loginUser.getUsername()).matches()) {
            if (tokenValidationService.isTokenValid(loginUser.getUsername(),loginUser.getPassword())) {
                if (userService.checkIfUserExists(loginUser)) {
                    AuthToken token = new AuthToken(tokenProvider.generateTokenFromGoogleToken(loginUser.getUsername(),loginUser.getPassword()));
                    return ResponseEntity.ok(token);
                } else {
                    throw new EntityNotFoundException("User Not Registered");
                }
            } else {
                throw new InvalidTokenException("for username " + loginUser.getUsername());
            }
        } else {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final String token = tokenProvider.generateToken(authentication);
            return ResponseEntity.ok(new AuthToken(token));
        }
    }

    /* get username of logged in user */
    private String getPrincipalUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


    /* TESTING METHOD */
    @PreAuthorize("hasRole('RECRUITER')")
    @RequestMapping(value="/user", method = RequestMethod.GET)
    public UserDetails adminPing(@RequestParam(value="username") String username){
        return userService.loadUserByUsername(getPrincipalUsername());
    }

    /* save new user */
    @PostMapping("/register")
    public ResponseEntity<Integer> saveUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.save(user).getId(), HttpStatus.CREATED);
    }

    /* get user details by username */
    @PreAuthorize("hasAnyRole('USER', 'RECRUITER')")
    @GetMapping("/findUser")
    public List getUserDetails(){
        User initialUser = userService.getUserDetailsByUsername(getPrincipalUsername());
        List newUser = new ArrayList();
        newUser.add(initialUser.getId());
        newUser.add(initialUser.getUsername());
        newUser.add(initialUser.getUserName());
        newUser.add(initialUser.getUserMobile());
        newUser.add(initialUser.getBusinessTitle());
        newUser.add(initialUser.getAccount());
        newUser.add(initialUser.getBusinessUnit());
        newUser.add(initialUser.getDateOfJoining());
        newUser.add(initialUser.getDesignation());
        newUser.add(initialUser.getLocation());
        newUser.add(initialUser.getPriorExperience());
        return newUser;
    }

    //Get skill
    @GetMapping("/findSkills")
    public ResponseEntity<List<Skill>> findSkill(){
        Integer id = userService.findOne(getPrincipalUsername()).getId();
        return new ResponseEntity<>(userSkillRepository.findByUser(id), HttpStatus.OK);
    }

    /* add new skill to user profile */
    @PostMapping("/addSkill")
    public Integer newSkill(@RequestBody Integer newSkillId){
        userSkillService.save(new UserSkill(userService.findOne(getPrincipalUsername()),
                skillService.getById(newSkillId)));
        return newSkillId;
    }

    /* get interviewers with required skills available for interview slot */
    @PostMapping("/findInterviewers")
    public ResponseEntity<List<UserAvailability>> findInterviewers(@RequestBody FindInterviewersRequest findInterviewersRequest) {
        return new ResponseEntity<>(userService.getAvailableInterviewers(findInterviewersRequest), HttpStatus.OK);
    }

}
