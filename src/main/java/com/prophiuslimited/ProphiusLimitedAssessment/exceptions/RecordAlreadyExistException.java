package com.prophiuslimited.ProphiusLimitedAssessment.exceptions;

import com.prophiuslimited.ProphiusLimitedAssessment.dtos.UserResponseDto;

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
