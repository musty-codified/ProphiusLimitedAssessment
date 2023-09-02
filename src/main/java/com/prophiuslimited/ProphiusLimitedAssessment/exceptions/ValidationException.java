package com.prophiuslimited.ProphiusLimitedAssessment.exceptions;

public class ValidationException extends RuntimeException{

    private String debugMessage;
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, String debugMessage) {
        super(message);
        this.debugMessage = debugMessage;
    }
}
