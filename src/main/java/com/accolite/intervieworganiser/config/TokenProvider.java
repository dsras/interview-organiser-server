package com.accolite.intervieworganiser.config;

import com.accolite.intervieworganiser.entities.User;
import com.accolite.intervieworganiser.service.UserService;
import io.jsonwebtoken.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider implements Serializable {

    @Autowired
    UserService userService;

    @Value("${jwt.token.validity}")
    public long TOKEN_VALIDITY;

    @Value("${jwt.signing.key}")
    public String SIGNING_KEY;

    @Value("${jwt.authorities.key}")
    public String AUTHORITIES_KEY;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(
            String token,
            Function<Claims, T> claimsResolver
    ) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts
            .parser()
            .setSigningKey(SIGNING_KEY)
            .parseClaimsJws(token)
            .getBody();
    }

    Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(Authentication authentication) {
        String authorities = authentication
            .getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        return Jwts
            .builder()
            .setSubject(authentication.getName())
            .claim(AUTHORITIES_KEY, authorities)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(
                new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000)
            )
            .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
            .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    UsernamePasswordAuthenticationToken getAuthenticationToken(
            final String token,
            final Authentication existingAuth,
            final UserDetails userDetails
    ) {
        final JwtParser jwtParser = Jwts.parser().setSigningKey(SIGNING_KEY);

        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);

        final Claims claims = claimsJws.getBody();

        final Collection<? extends GrantedAuthority> authorities = Arrays
            .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                "",
                authorities
        );
    }

    public String generateTokenFromGoogleToken(
            String email,
            String googleToken
    ) {
        User user = userService.getByEmail(email);
        Set<SimpleGrantedAuthority> autho = userService.getAuthority(user);
        String authorities = autho
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));
        return Jwts
            .builder()
            .setSubject(email)
            .setId(googleToken)
            .claim(AUTHORITIES_KEY, authorities)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(
                new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000)
            )
            .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
            .compact();
    }

    /*******************************************************************************************/

    public String getJtiFromToken(String token) {
        return getClaimFromToken(token, Claims::getId);
    }

    public void setSigningKey(String signingKey) {
        SIGNING_KEY = signingKey;
    }

    public void setAuthoritiesKey(String authoritiesKey) {
        AUTHORITIES_KEY = authoritiesKey;
    }

    public void setTokenValidity(Long tokenValidity) {
        TOKEN_VALIDITY = tokenValidity;
    }
}
