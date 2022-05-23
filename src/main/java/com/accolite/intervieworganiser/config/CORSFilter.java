package com.accolite.intervieworganiser.config;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filters request to allow cross-origin request
 */
public class CORSFilter implements Filter {

    HttpServletResponse response;

    /**
     * Filtering method called
     *
     * @param req the request
     * @param res the response
     * @param chain the filter chain
     * @throws IOException thrown by FilterChain doFilter
     * @throws ServletException thrown by FilterChain doFilter
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, " +
                "Authorization, Origin, Accept, Access-Control-Request-Method, Access-Control-Request-Headers");
        if ("OPTIONS".equalsIgnoreCase(((HttpServletRequest) req).getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
    }

    /**
     * Gets response
     *
     * @return the response
     */
    public HttpServletResponse getResponse(){ return  response;}

}