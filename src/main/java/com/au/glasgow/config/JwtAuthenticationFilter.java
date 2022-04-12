package com.au.glasgow.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.au.glasgow.entities.Role;
import io.jsonwebtoken.ExpiredJwtException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.au.glasgow.exception.InvalidTokenException;
import com.au.glasgow.service.TokenValidationService;
import com.au.glasgow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

@Order(Integer.MIN_VALUE)
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.header.string}")
    public String HEADER_STRING;

    @Value("Bearer")
    public String TOKEN_PREFIX;

    @Resource(name = "userService")
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    TokenValidationService tokenValidationService;

    @Autowired
    UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);
        System.err.println("REQ:" + req.getRequestURI());
        System.err.println("IN JWTAUTHENTICATIONFILTER: " + header);
        String username = null;
        String authToken = null;
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            authToken = header.replace(TOKEN_PREFIX, "");
            try {
                // SSO Login
                String token = jwtTokenUtil.getJtiFromToken(authToken);
                if (null != token) {
                    username = jwtTokenUtil.getUsernameFromToken(authToken);
                    if (!tokenValidationService.isTokenValid(username, token)) {
                        throw new InvalidTokenException();
                    } else {
//                        User user = userService.getByEmail(username);
//                        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
//                        UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthenticationToken(
//                                authToken, SecurityContextHolder.getContext().getAuthentication(), userDetails);
//                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                        Set<Role> roles = new HashSet<>();
                        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                        Role role1 = new Role();
                        role1.setName("ROLE_ADMIN");
                        roles.add(role1);
                        for (Role role : roles) {
                            authorities.add(new SimpleGrantedAuthority(role.getName()));
                        }
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken("dummy", "", authorities);
                        logger.info("authenticated user " + username + ", setting security context");
                        logger.info("authenticated user authorities " + authentication);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                } else { // Conventional Login
                    username = jwtTokenUtil.getUsernameFromToken(authToken);
                }
            } catch (IllegalArgumentException e) {
                logger.error("An error occurred while fetching Username from Token", e);
            } catch (ExpiredJwtException e) {
                logger.warn("The token has expired", e);
            }catch(Exception ex) {
                throw new InvalidTokenException();
            }} else {
            logger.warn("Couldn't find bearer string, header will be ignored");
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthenticationToken(authToken,
                        SecurityContextHolder.getContext().getAuthentication(), userDetails);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                logger.info("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(req, res);
    }
}
