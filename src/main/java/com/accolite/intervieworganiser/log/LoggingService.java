package com.accolite.intervieworganiser.log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;

public interface LoggingService {
    void logRequest(HttpServletRequest httpServletRequest, Object body);

    void logResponse(
        HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse,
        Object body
    );
}
