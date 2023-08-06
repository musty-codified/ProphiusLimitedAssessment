package com.prophiuslimited.ProphiusLimitedAssessment.exceptions;


import com.prophiuslimited.ProphiusLimitedAssessment.dtos.responses.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotException(ResourceNotFoundException ex, WebRequest request) {
        ErrorResponse errorMessage = new ErrorResponse(new Date(), ex.getMessage());
        errorMessage.setDebugMessage("Records not found");
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        ErrorResponse errorMessage = new ErrorResponse(new Date(), ex.getMessage());
        errorMessage.setDebugMessage("User not found");
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {RecordAlreadyExistException.class})
    public ResponseEntity<Object> handleRecordAlreadyExistException(RecordAlreadyExistException ex, WebRequest request) {
        ErrorResponse errorMessage = new ErrorResponse(new Date(), ex.getMessage());
        errorMessage.setDebugMessage("Record already exist");

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(value = {UnauthorizedUserException.class})
    public ResponseEntity<Object> handleUnauthorizedUserException(UnauthorizedUserException ex, WebRequest request) {
        ErrorResponse errorMessage = new ErrorResponse(new Date(), ex.getMessage());
        errorMessage.setDebugMessage("You're not authorized to update this post");

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleOtherServiceException(Exception ex, WebRequest request) {
        ErrorResponse errorMessage = new ErrorResponse(new Date(), ex.getMessage());
        errorMessage.setDebugMessage("You have encountered an error");

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
