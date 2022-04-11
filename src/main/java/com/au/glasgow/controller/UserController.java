package com.au.glasgow.controller;

import com.au.glasgow.config.TokenProvider;
import com.au.glasgow.dto.AuthToken;
import com.au.glasgow.dto.LoginUser;
import com.au.glasgow.entities.User;
import com.au.glasgow.exception.InvalidTokenException;
import com.au.glasgow.dto.AvailableUsersRequest;
import com.au.glasgow.service.TokenValidationService;
import com.au.glasgow.serviceImpl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    /* for testing until authentication works */
    private String username = "emer.sweeney@accolitedigital.com";
    //        return SecurityContextHolder.getContext().getAuthentication().toString();

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

    /* get user by username */
    @GetMapping("/user")
    public UserDetails getUser(@RequestParam(value="username", required = true) String username){
        return userService.loadUserByUsername(username);
    }

    /* create new user */
    @PostMapping("/new")
    public User newUser(@RequestBody User newUser) {
        return userService.save(newUser);
    }

    /* get interviewers available for interview */
    @PostMapping("/available")
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

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Integer> saveUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.save(user).getId(), HttpStatus.CREATED);
    }
}
