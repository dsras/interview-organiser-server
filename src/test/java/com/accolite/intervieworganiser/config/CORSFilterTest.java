package com.accolite.intervieworganiser.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CORSFilterTest {

    @Mock
    FilterChain chain;

    @Mock
    HttpServletResponse request;

    CORSFilter corsFilter = new CORSFilter();

//    @Test
//    void testDoFilterAddsCorrectHeadersToResponse() throws IOException, ServletException {
//        HttpServletResponse response = new MockHttpServletResponse();
//        doNothing().when(chain).doFilter(any(), any());
//        when(((HttpServletRequest) request).getMethod()).thenReturn("OPTIONS");
//        corsFilter.doFilter((ServletRequest) request, response, chain);
//        HttpServletResponse generatedResponse = corsFilter.getResponse();
//        assertEquals("*",generatedResponse.getHeader("Access-Control-Allow-Origin"));
//        assertEquals("true", generatedResponse.getHeader("Access-Control-Allow-Credentials"));
//        assertEquals("POST, GET, PUT, OPTIONS, DELETE",generatedResponse.getHeader("Access-Control-Allow-Methods"));
//        assertEquals("3600",generatedResponse.getHeader("Access-Control-Max-Age"));
//        assertEquals("X-Requested-With, Content-Type, Authorization, Origin, Accept, " +
//                        "Access-Control-Request-Method, Access-Control-Request-Headers",
//                generatedResponse.getHeader("Access-Control-Allow-Headers"));
//    }

}
