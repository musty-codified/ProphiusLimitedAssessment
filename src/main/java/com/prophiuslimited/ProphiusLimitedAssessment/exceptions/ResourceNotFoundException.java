package com.prophiuslimited.ProphiusLimitedAssessment.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    private String debugMessage;

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, String debugMessage) {
        super(message);
        this.debugMessage = debugMessage;
    }
}
