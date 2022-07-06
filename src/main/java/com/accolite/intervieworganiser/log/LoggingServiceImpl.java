package com.accolite.intervieworganiser.log;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Concrete logging service to log HTTP requests and responses.
 */
@Component
@Log
public class LoggingServiceImpl implements LoggingService {

    Logger logger = LoggerFactory.getLogger(LoggingServiceImpl.class);

    /**
     * Logs requests.
     * <p>
     * Logs request method, path, headers, parameters (if applicable) and body (if applicable).
     * </p>
     *
     * @param httpServletRequest the incoming request
     * @param body the request body
     */
    @Override
    public void logRequest(HttpServletRequest httpServletRequest, Object body) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, String> parameters = buildParametersMap(httpServletRequest);

        stringBuilder.append("REQUEST ");
        stringBuilder
            .append("method=[")
            .append(httpServletRequest.getMethod())
            .append("] ");
        stringBuilder
            .append("path=[")
            .append(httpServletRequest.getRequestURI())
            .append("] ");
        stringBuilder
            .append("headers=[")
            .append(buildHeadersMap(httpServletRequest))
            .append("] ");

        if (!parameters.isEmpty()) {
            stringBuilder
                .append("parameters=[")
                .append(parameters)
                .append("] ");
        }

        if (body != null) {
            stringBuilder.append("body=[" + body + "]");
        }

        logger.info(stringBuilder.toString());
    }

    /**
     * Logs responses.
     * <p>
     * Logs response method, path, header and body.
     * </p>
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param body
     */
    @Override
    public void logResponse(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            Object body
    ) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("RESPONSE ");
        stringBuilder
            .append("method=[")
            .append(httpServletRequest.getMethod())
            .append("] ");
        stringBuilder
            .append("path=[")
            .append(httpServletRequest.getRequestURI())
            .append("] ");
        stringBuilder
            .append("responseHeaders=[")
            .append(buildHeadersMap(httpServletResponse))
            .append("] ");
        stringBuilder.append("responseBody=[").append(body).append("] ");

        logger.info(stringBuilder.toString());
    }

    /**
     * Gets parameters from request.
     *
     * @param httpServletRequest the incoming request
     * @return a map of parameter names and values
     */
    private Map<String, String> buildParametersMap(
            HttpServletRequest httpServletRequest
    ) {
        Map<String, String> resultMap = new HashMap<>();
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            String value = httpServletRequest.getParameter(key);
            resultMap.put(key, value);
        }

        return resultMap;
    }

    /**
     * Gets headers from request.
     *
     * @param request the incoming request
     * @return a map of header names and values
     */
    private Map<String, String> buildHeadersMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }

    /**
     * Gets headers from response.
     *
     * @param response the outgoing response
     * @return a map of header names and values
     */
    private Map<String, String> buildHeadersMap(HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();

        Collection<String> headerNames = response.getHeaderNames();
        for (String header : headerNames) {
            map.put(header, response.getHeader(header));
        }

        return map;
    }
}
