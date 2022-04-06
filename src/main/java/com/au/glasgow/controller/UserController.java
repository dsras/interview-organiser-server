package com.au.glasgow.controller;

import com.au.glasgow.config.TokenProvider;
import com.au.glasgow.dto.AuthToken;
import com.au.glasgow.dto.LoginUser;
import com.au.glasgow.dto.UserResponse;
import com.au.glasgow.entities.User;
import com.au.glasgow.exception.InvalidTokenException;
import com.au.glasgow.requestModels.AvailableUsersRequest;
import com.au.glasgow.service.TokenValidationService;
import com.au.glasgow.serviceImpl.UserService;
import org.json.JSONObject;
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
import java.util.regex.Pattern;

/*
user controller
handles user-related requests
 */

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenValidationService tokenValidationService;

    private TokenProvider tokenProvider;
    private AuthenticationManager authenticationManager;
    private final String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@accolitedigital.com";

    public UserController(AuthenticationManager authenticationManager, TokenProvider tokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    /* test method */
    @GetMapping("/welcome")
    public String welcome(){
        return JSONObject.quote("Welcome");
//        return SecurityContextHolder.getContext().getAuthentication();
    }

    /* get user by username
    * access: all */
    @GetMapping("/user")
    @PreAuthorize("hasRole('ADMIN', 'RECRUITER', 'USER')")
//    @PreAuthorize("hasRole('RECRUITER')")
    public UserDetails getUser(@RequestParam(value="username", required = true) String username){
        return userService.loadUserByUsername(username);
    }

    /* create new user */
    @PostMapping("/new")
    public User newUser(@RequestBody User newUser) {
        return userService.save(newUser);
    }

    /* get interviewers available for interview
    * access: only admins and recruiters */
    @PostMapping("/available")
    @PreAuthorize("hasAnyRole('ADMIN','RECRUITER')")
    public AvailableUsersRequest getAvailableUsers(@RequestBody AvailableUsersRequest availableUsersRequest){
        AvailableUsersRequest test = availableUsersRequest;
        /*
        need a service method to call
        that takes this request
        finds skills by ID
        finds interviewers that
        (1) have these skills
        (2) have availability on this date between these times
        (3) do not have interviews booked on this date between these times
         */
        return test;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> generateToken(@RequestBody LoginUser loginUser)
            throws AuthenticationException, InvalidTokenException {
        System.err.println("IN USERCONTROLLER.GENERATETOKEN");
        if (Pattern.compile(regex).matcher(loginUser.getUsername()).matches()) {
            System.err.println("IN USERCONTROLLER USERNAME PATTERN MATCHING");
            if (tokenValidationService.isTokenValid(loginUser.getUsername(),loginUser.getPassword())) {
                System.err.println("IN USERCONTROLLER TOKEN IS VALID LOOP");
                if (userService.checkIfUserExists(loginUser)) {
                    System.err.println("IN USERCONTROLLER USER EXISTS LOOP");
                    return ResponseEntity.ok(new AuthToken(tokenProvider.generateTokenFromGoogleToken(loginUser.getUsername(),loginUser.getPassword())));
                } else {
                    throw new EntityNotFoundException("User Not Registered with RaFT");
                }
            } else {
                throw new InvalidTokenException("for username " + loginUser.getUsername());
            }
        } else {
            System.err.println("IN USERCONTROLLER FINAL ELSE BLOCK");
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final String token = tokenProvider.generateToken(authentication);
            return ResponseEntity.ok(new AuthToken(token));
        }
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Integer> saveUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.save(user).getId(), HttpStatus.CREATED);
    }
}
