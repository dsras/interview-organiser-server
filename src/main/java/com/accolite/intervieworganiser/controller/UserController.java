package com.accolite.intervieworganiser.controller;

import com.accolite.intervieworganiser.config.TokenProvider;
import com.accolite.intervieworganiser.dto.AuthToken;
import com.accolite.intervieworganiser.dto.LoginUser;
import com.accolite.intervieworganiser.entities.User;
import com.accolite.intervieworganiser.exception.InvalidTokenException;
import com.accolite.intervieworganiser.service.TokenValidationService;
import com.accolite.intervieworganiser.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Provides handling of user-related requests
 */

@RestController
@RequestMapping("/users")
public class UserController {

    private AuthenticationManager authenticationManager;
    private TokenProvider tokenProvider;
    private TokenValidationService tokenValidationService;
    private UserService userService;

    /**
     * Parameterised constructor.
     *
     * @param authenticationManager to process authentication request
     * @param tokenProvider to generate access tokens
     * @param tokenValidationService to validate tokens
     * @param userService user service layer
     */
    public UserController(
            @Autowired AuthenticationManager authenticationManager,
            @Autowired TokenProvider tokenProvider,
            @Autowired TokenValidationService tokenValidationService,
            @Autowired UserService userService
    ) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.tokenValidationService = tokenValidationService;
        this.userService = userService;
    }

    /**
     * <p>
     * Generates authentication token.
     * </p>
     *
     * @param loginUser the login user
     * @return the generated token
     * @throws AuthenticationException
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> generateToken(@RequestBody LoginUser loginUser)
            throws AuthenticationException {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@accolitedigital.com";
        if (Pattern.compile(regex).matcher(loginUser.getUsername()).matches()) {
            if (
                tokenValidationService.isTokenValid(
                    loginUser.getUsername(),
                    loginUser.getPassword()
                )
            ) {
                if (userService.checkIfUserExists(loginUser)) {
                    AuthToken token = new AuthToken(
                            tokenProvider.generateTokenFromGoogleToken(
                                loginUser.getUsername(),
                                loginUser.getPassword()
                            )
                    );
                    return ResponseEntity.ok(token);
                } else {
                    throw new EntityNotFoundException("User Not Registered");
                }
            } else {
                throw new InvalidTokenException(
                        "for username " + loginUser.getUsername()
                );
            }
        } else {
            final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
            );
            SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);
            final String token = tokenProvider.generateToken(authentication);
            return ResponseEntity.ok(new AuthToken(token));
        }
    }

    /**
     * Gets user's personal details.
     * <p>
     * Returns personal details as a list.
     * </p>
     *
     * @return list of user details
     */
    @PreAuthorize("hasAnyRole('USER', 'RECRUITER')")
    @GetMapping("/{username}")
    public User getUserDetails(@PathVariable("username") String username) {
        return userService.getUserDetailsByUsername(username);
    }

    @PreAuthorize("hasAnyRole('USER', 'RECRUITER')")
    @GetMapping("/{username}/roles")
    public List<String> getUserRoles(@PathVariable("username") String username) {
        User user = userService.getByEmail(username);
        List<String> myAuths = userService.getRoles(user);
        List<String> output = new ArrayList<>();
        for (String role : myAuths) {
            output.add(role);
        }
        return output;
    }
}
