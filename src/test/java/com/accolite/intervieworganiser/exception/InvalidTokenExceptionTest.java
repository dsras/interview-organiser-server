package com.accolite.intervieworganiser.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InvalidTokenExceptionTest {

    /**
     * Tests all constructors of {@link InvalidTokenException} correctly create exception
     * and that getMessage & toString return correct message.
     */
    @Test
    void testCorrectExceptionsCreated() {
        /* test toString returns message */
        InvalidTokenException invalidTokenExceptionDefault = new InvalidTokenException();
        assertEquals("Invalid / Expired Token", invalidTokenExceptionDefault.toString());

        /* test custom message is added to default message */
        InvalidTokenException invalidTokenExceptionWithMessage = new InvalidTokenException("Please try again");
        assertEquals("Invalid / Expired Token: Please try again", invalidTokenExceptionWithMessage.getMessage());

        /* test custom message and cause message are returned */
        InvalidTokenException invalidTokenExceptionWithMessageAndCause =
            new InvalidTokenException("Please try again", new Throwable("Token has expired"));
        assertEquals("Please try again, Token has expired", invalidTokenExceptionWithMessageAndCause.getMessage());
    }
}
