package com.au.glasgow.controller;

//import com.au.glasgow.config.TokenProvider;
//import com.au.glasgow.dto.AuthToken;
//import com.au.glasgow.dto.LoginUser;
//import com.au.glasgow.entities.User;
//import com.au.glasgow.exception.InvalidTokenException;
//import com.au.glasgow.dto.FindInterviewersRequest;
//import com.au.glasgow.service.TokenValidationService;
//import com.au.glasgow.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.*;
//
//import javax.persistence.EntityNotFoundException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Pattern;
//
///*
//user controller
//handles user-related requests
// */
//
//@CrossOrigin(origins = "*", maxAge = 3600)
//@RestController
//@RequestMapping("/users")
//public class UserController {
//
//    /* for testing until authentication works */
//    private String username = "emer.sweeney@accolitedigital.com";
//    //        return SecurityContextHolder.getContext().getAuthentication().toString();
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private TokenValidationService tokenValidationService;
//
//    private TokenProvider tokenProvider;
//    private AuthenticationManager authenticationManager;
//
//    public UserController(AuthenticationManager authenticationManager, TokenProvider tokenProvider, UserService userService) {
//        this.authenticationManager = authenticationManager;
//        this.tokenProvider = tokenProvider;
//        this.userService = userService;
//    }
//
//
//
//
//    /* get user by username */
//    @GetMapping("/user")
//    @PreAuthorize("hasRole('ADMIN')")
//    public UserDetails getUser(@RequestParam(value="username", required = true) String username){
//        return userService.loadUserByUsername(username);
//    }
//
//
//
//
//
//
//
//    /* get interviewers available for interview */
//    /*
//    ### POST request: interviewers with required skills available for time interval on certain date
//    - request body: a [FindInterviewers] object
//    - response body: list (of variable length) of [AvailableInterviewer] objects
//    (a list of the available interviewers, IDs needed for POST request to created interview)
//     */
//    @PostMapping("/findInterviewers")
//    public FindInterviewersRequest findInterviewers(@RequestBody FindInterviewersRequest findInterviewersRequest){
//        return findInterviewersRequest;
//    }
//
//    @PostMapping("/authenticate")
//    public ResponseEntity<?> generateToken(@RequestBody LoginUser loginUser)
//            throws AuthenticationException, InvalidTokenException {
//        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@accolitedigital.com";
//        if (Pattern.compile(regex).matcher(loginUser.getUsername()).matches()) {
//            if (tokenValidationService.isTokenValid(loginUser.getUsername(),loginUser.getPassword())) {
//                if (userService.checkIfUserExists(loginUser)) {
//                    AuthToken token = new AuthToken(tokenProvider.generateTokenFromGoogleToken(loginUser.getUsername(),loginUser.getPassword()));
//                    return ResponseEntity.ok(token);
//                } else {
//                    throw new EntityNotFoundException("User Not Registered");
//                }
//            } else {
//                throw new InvalidTokenException("for username " + loginUser.getUsername());
//            }
//        } else {
//            final Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            final String token = tokenProvider.generateToken(authentication);
//            return ResponseEntity.ok(new AuthToken(token));
//        }
//    }
//
//    /* save new user */
//    @PostMapping("/register")
//    public ResponseEntity<Integer> saveUser(@RequestBody User user) {
//        return new ResponseEntity<>(userService.save(user).getId(), HttpStatus.CREATED);
//    }
//
//    /* get user details by username */
//    @GetMapping("/findUser")
//    public List getUserDetails(){
//        User initialUser = userService.getUserDetailsByUsername(username);
//        List newUser = new ArrayList();
//        newUser.add(initialUser.getId());
//        newUser.add(initialUser.getUsername());
//        newUser.add(initialUser.getUserName());
//        newUser.add(initialUser.getUserMobile());
//        newUser.add(initialUser.getBusinessTitle());
//        newUser.add(initialUser.getAccount());
//        newUser.add(initialUser.getBusinessUnit());
//        newUser.add(initialUser.getDateOfJoining());
//        newUser.add(initialUser.getDesignation());
//        newUser.add(initialUser.getLocation());
//        newUser.add(initialUser.getPriorExperience());
//        return newUser;
//    }
//}

import com.au.glasgow.config.TokenProvider;
import com.au.glasgow.dto.AuthToken;
import com.au.glasgow.dto.LoginUser;
import com.au.glasgow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> generateToken(@RequestBody LoginUser loginUser) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(new AuthToken(token));
    }

    @RequestMapping(value="/user", method = RequestMethod.GET)
    public UserDetails getUser(@RequestParam(value="username", required = true) String username){
        return userService.loadUserByUsername(username);
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @RequestMapping(value="/superadminping", method = RequestMethod.GET)
    public String superAdminPing(){
        return "Only superAdmins Can Read This";
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value="/userping", method = RequestMethod.GET)
    public String userPing(){
        return "Only Users Can Read This";
    }

    @PreAuthorize("hasRole('SUPERUSER')")
    @RequestMapping(value="/superuserping", method = RequestMethod.GET)
    public String superUserPing(){
        return "Only super Users Can Read This";
    }

    /*
     * @PreAuthorize("hasRole('USER')")
     *
     * @RequestMapping(value="/userping", method = RequestMethod.GET) public String
     * userPing(){ return "Any User Can Read This"; }
     */

}