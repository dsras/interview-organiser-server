package com.accolite.intervieworganiser.exception;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests {@link CustomGlobalExceptionHandler}.
 */
@ExtendWith(MockitoExtension.class)
public class CustomGlobalExceptionHandlerTest {

    CustomGlobalExceptionHandler exceptionHandler = new CustomGlobalExceptionHandler();

    /* headers, status, request, binding result & method parameter for mocking */
    static HttpHeaders headers = new HttpHeaders();
    static HttpStatus status = HttpStatus.OK;
    static WebRequest webRequest = mock(WebRequest.class);
    BindingResult result = mock(BindingResult.class);
    MethodParameter parameter = mock(MethodParameter.class);

    @BeforeClass
    public static void init() {
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("test header name", "test header content");
    }

    /**
     * Tests that response body consists of corrects headers, status and error message
     */
    @Test
    void testCorrectResponseCreated() {
        /* mock errors returned from binding result getFieldErrors */
        FieldError fieldError = new FieldError("Error", "error", "error message");
        List<FieldError> fieldErrorList = new ArrayList<>();
        fieldErrorList.add(fieldError);
        when(result.getFieldErrors()).thenReturn(fieldErrorList);

        /* assert handleMethodArgumentNotValid returns correct headers, status and error message */
        ResponseEntity<Object> responseEntity = exceptionHandler.handleMethodArgumentNotValid(
            new MethodArgumentNotValidException(parameter, result),
            headers,
            status,
            webRequest
        );
        assertTrue(Objects.requireNonNull(responseEntity.getBody()).toString().contains("errors=[error message]"));
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
        assertEquals(headers, responseEntity.getHeaders());
    }
}
