package com.prophiuslimited.ProphiusLimitedAssessment.exceptions;

public class RecordAlreadyExistException extends RuntimeException {
    private String debugMessage;
    public RecordAlreadyExistException(String message) {
        super(message);
    }

    public RecordAlreadyExistException(String message, String debugMessage) {
        super(message);
        this.debugMessage = debugMessage;
    }
}
