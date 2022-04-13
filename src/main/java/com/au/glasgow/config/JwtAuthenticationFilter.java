package com.au.glasgow.config;

import com.au.glasgow.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.header.string}")
    public String HEADER_STRING;

    @Value("${jwt.token.prefix}")
    public String TOKEN_PREFIX;

    @Resource(name = "userService")
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        /*
         * String header = req.getHeader(HEADER_STRING); String username = null; String
         * authToken = null; if (header != null && header.startsWith(TOKEN_PREFIX)) {
         * authToken = header.replace(TOKEN_PREFIX,""); try { username =
         * jwtTokenUtil.getUsernameFromToken(authToken); } catch
         * (IllegalArgumentException e) {
         * logger.error("An error occurred while fetching Username from Token", e); }
         * catch (ExpiredJwtException e) { logger.warn("The token has expired", e); }
         * catch(SignatureException e){
         * logger.error("Authentication Failed. Username or Password not valid."); } }
         * else { logger.warn("Couldn't find bearer string, header will be ignored"); }
         */
        String username = "pritesh";
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            Set<Role> roles = new HashSet<>();
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            Role role1 = new Role();
            role1.setName("ROLE_ADMIN");
            Role role2 = new Role();
            role2.setName("ROLE_USER");
            roles.add(role1);
            roles.add(role2);
            for (Role role : roles) {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("dummy", "", authorities);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
            logger.info("authenticated user " + username + ", setting security context");
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        chain.doFilter(req, res);
    }
}