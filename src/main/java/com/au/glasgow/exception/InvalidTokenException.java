package com.au.glasgow.exception;

public class InvalidTokenException extends RuntimeException {

    String message;

    public InvalidTokenException() {
        message = "Invalid / Expired Token ";
    }

    public InvalidTokenException(String str) {
        message = "Invalid / Expired Token " + str;
    }

    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getMessage() {
        return message;

    }

    public String toString() {
        return message;
    }
}
