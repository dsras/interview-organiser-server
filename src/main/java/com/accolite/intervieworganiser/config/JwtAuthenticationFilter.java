package com.accolite.intervieworganiser.config;

import com.accolite.intervieworganiser.entities.User;
import com.accolite.intervieworganiser.exception.InvalidTokenException;
import com.accolite.intervieworganiser.service.TokenValidationService;
import com.accolite.intervieworganiser.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Provides authentication filter executed once per request.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.header.string}")
    public String HEADER_STRING;

    @Value("${jwt.token.prefix}")
    public String TOKEN_PREFIX;

    @Resource(name = "userService")
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private TokenValidationService tokenValidationService;

    /**
     * Verifies authentication details of request
     *
     * @param req the request
     * @param res the response
     * @param chain the filter chain
     * @throws IOException thrown by FilterChain doFilter
     * @throws ServletException thrown by FilterChain doFilter
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain
    )
            throws ServletException,
                IOException {
        String header = req.getHeader(HEADER_STRING);
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
                        User user = userService.getByEmail(username);
                        UserDetails userDetails = userDetailsService.loadUserByUsername(
                            user.getUsername()
                        );
                        UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthenticationToken(
                            authToken,
                            SecurityContextHolder
                                .getContext()
                                .getAuthentication(),
                            userDetails
                        );
                        authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                .buildDetails(req)
                        );
                        logger.info(
                            "authenticated user "
                                +
                                username
                                +
                                ", setting security context"
                        );
                        SecurityContextHolder
                            .getContext()
                            .setAuthentication(authentication);
                    }
                } else { // Conventional Login
                    username = jwtTokenUtil.getUsernameFromToken(authToken);
                }
            } catch (IllegalArgumentException e) {
                logger.error(
                    "An error occurred while fetching Username from Token",
                    e
                );
            } catch (ExpiredJwtException e) {
                logger.warn("The token has expired", e);
            } catch (Exception ex) {
                throw new InvalidTokenException();
            }
        } else {
            logger.warn("Couldn't find bearer string, header will be ignored");
        }
        if (
            username != null
                &&
                SecurityContextHolder.getContext().getAuthentication() == null
        ) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(
                username
            );

            if (
                Boolean.TRUE.equals(
                    jwtTokenUtil.validateToken(authToken, userDetails)
                )
            ) {
                UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthenticationToken(
                    authToken,
                    SecurityContextHolder.getContext().getAuthentication(),
                    userDetails
                );
                authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(req)
                );
                logger.info(
                    "authenticated user "
                        +
                        username
                        +
                        ", setting security context"
                );
                SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);
            }
        }

        chain.doFilter(req, res);
    }
}
