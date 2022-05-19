package com.accolite.intervieworganiser.exception;

public class InvalidTokenException extends RuntimeException {

    String message = "Invalid / Expired Token";


    public InvalidTokenException(){}

    public InvalidTokenException(String str) {
        message += ": " + str;
    }

    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
        this.message=message + ", " + cause.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
