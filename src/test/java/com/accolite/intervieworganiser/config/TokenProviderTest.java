package com.accolite.intervieworganiser.config;

import com.accolite.intervieworganiser.entities.User;
import com.accolite.intervieworganiser.service.UserService;
import io.jsonwebtoken.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import utility.Constants;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenProviderTest {

    @Mock
    UserService userService;

    static TokenProvider tokenProvider = new TokenProvider();
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    String id = "123";
    String issuer = "server";
    static String subject = "tester@accolitedigital.com";
    static Long expiration = 36000L;
    static String authoritiesKey = "roles";
    String token = createJWT(id, issuer, subject, expiration);
    static Authentication authentication;
    static List<SimpleGrantedAuthority> authorities = new ArrayList<>();


    @BeforeAll
    static void init(){
        /* set required attributes of token provider instance */
        tokenProvider.setSigningKey("signingkey");
        tokenProvider.setAuthoritiesKey(authoritiesKey);
        tokenProvider.setTokenValidity(expiration);

        /* create authentication with tester subject and user authority */
        authentication = createAuthenticationForTesting();

        /* create authorities with user role */
        SimpleGrantedAuthority userAuthority = new SimpleGrantedAuthority("User");
        authorities.add(userAuthority);
    }

    @Test
    void testGetUsernameFromToken(){
        String returnedUsername = tokenProvider.getUsernameFromToken(token);
        assertEquals(subject, returnedUsername);
    }

    @Test
    void testGetJtiToken(){
        String returnedJti = tokenProvider.getJtiFromToken(token);
        assertEquals(id, returnedJti);
    }

    @Test
    void testIsTokenExpired(){
        String validToken = token;
        String expiredToken = createJWT(id, issuer, subject, 1L);

        /* assert method returns false for valid token
        and token expired exception is thrown for expired token
         */
        assertFalse(tokenProvider.isTokenExpired(validToken));
        Exception exception = assertThrows(ExpiredJwtException.class, () -> {
            tokenProvider.isTokenExpired(expiredToken);
        });
    }

    @Test
    void testValidateTokenIsTrueWithValidUsernameAndValidToken(){
        String username = "tester";
        UserDetails userDetails = createUserDetailsForTesting(subject);

        /* assert validateToken returns true */
        assertTrue(tokenProvider.validateToken(token, userDetails));
    }

    @Test
    void testValidateTokenIsFalseWithInvalidUsernameAndValidToken(){
        String username = "tester";
        UserDetails userDetails = createUserDetailsForTesting("other_user@accolitedigital.com");

        /* assert validateToken returns false due to invalid username */
        assertFalse(tokenProvider.validateToken(token, userDetails));
    }

    @Test
    void testValidateTokenThrowsExceptionWithValidUsernameAndExpiredToken(){
        String username = "tester";
        String expiredToken = createJWT(id, issuer, subject, 1L);
        UserDetails userDetails = createUserDetailsForTesting(subject);

        /* assert expired token exception is thrown by validateToken */
        Exception exception = assertThrows(ExpiredJwtException.class, () -> {
            tokenProvider.validateToken(expiredToken, userDetails);
        });
    }

    @Test
    void testValidateTokenThrowsExceptionWithInvalidUsernameAndExpiredToken(){
        String username = "tester";
        String expiredToken = createJWT(id, issuer, subject, 1L);
        UserDetails userDetails = createUserDetailsForTesting("other_user@accolitedigital.com");

        /* assert expired token exception is thrown by validateToken */
        Exception exception = assertThrows(ExpiredJwtException.class, () -> {
            tokenProvider.validateToken(expiredToken, userDetails);
        });
    }

    @Test
    void testGenerateTokenCreatesCorrectToken(){

        /* create token and record time before and after creation */
        Date dateBeforeCreation = new Date(System.currentTimeMillis());
        String generatedToken = tokenProvider.generateToken(authentication);
        Date dateAfterCreation = new Date(System.currentTimeMillis());
        Date latestExpirationDate = new Date(dateAfterCreation.getTime()+expiration*1000);

        /* assert correct subject */
        assertEquals(subject, tokenProvider.getUsernameFromToken(generatedToken));

        /* assert expiration date is in expected range */
        Date expirationDate = tokenProvider.getClaimFromToken(generatedToken, Claims::getExpiration);
        assertTrue(expirationDate.before(latestExpirationDate) && expirationDate.after(dateAfterCreation));
    }

    @Test
    void testGetAuthenticationTokenCreatesCorrectToken(){
        UsernamePasswordAuthenticationToken generatedToken = tokenProvider.getAuthenticationToken(token,
                authentication, createUserDetailsForTesting(subject));

        /* assert one authority is added to token authorities */
        Collection<GrantedAuthority> returnedAuthorities = generatedToken.getAuthorities();
        assertEquals(1, returnedAuthorities.size());

        /* assert authority contains "User" */
        String authoritiesAsString = returnedAuthorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        assertTrue(authoritiesAsString.contains("User"));
    }

//    @Test
    void testGenerateTokenFromGoogleTokenGeneratesCorrectToken(){
        /* mock user service calls */
        User user = new User(subject, Constants.password, Constants.email,
                Constants.interviewerName, Constants.interviewerBusTitle);
        when(userService.getByEmail(subject)).thenReturn(user);

        Set<SimpleGrantedAuthority> authoritySet = new HashSet<>(authorities);
        when(userService.getAuthority(user)).thenReturn(authoritySet);

        String googleToken = "google-token";

        String generatedToken = tokenProvider.generateTokenFromGoogleToken(subject, googleToken);
        System.err.println(generatedToken);
        assertEquals(subject, tokenProvider.getUsernameFromToken(generatedToken));
        assertEquals(googleToken, tokenProvider.getJtiFromToken(generatedToken));
    }

    static UserDetails createUserDetailsForTesting(String username){
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {return authorities;}
            @Override
            public String getPassword() {return null;}
            @Override
            public String getUsername() {return username;}
            @Override
            public boolean isAccountNonExpired() {return false;}
            @Override
            public boolean isAccountNonLocked() {return false;}
            @Override
            public boolean isCredentialsNonExpired() {return false;}
            @Override
            public boolean isEnabled() {return false;}
        };
    }

    static Authentication createAuthenticationForTesting(){
        return new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {return authorities;}
            @Override
            public Object getCredentials() {return null;}
            @Override
            public Object getDetails() {return null;}
            @Override
            public Object getPrincipal() {return null;}
            @Override
            public boolean isAuthenticated() {return true;}
            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {}
            @Override
            public String getName() {return subject;}
        };
    }

    String createJWT(String id, String issuer, String subject, long expiration) {

        long nowMs = System.currentTimeMillis();
        Date now = new Date(nowMs);

        /* set JWT claims */
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .claim(authoritiesKey, authorities)
                .signWith(signatureAlgorithm, "signingkey");

        /* set expiration if applicable */
        if (expiration > 0) {
            long expMs = nowMs + expiration;
            Date exp = new Date(expMs);
            builder.setExpiration(exp);
        }

        /* return JWT string */
        return builder.compact();
    }
}
