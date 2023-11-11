package com.prophiuslimited.ProphiusLimitedAssessment.exceptions;


import com.prophiuslimited.ProphiusLimitedAssessment.dtos.responses.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleResourceNotException(ResourceNotFoundException ex, WebRequest request) {
        ErrorResponse errorMessage = new ErrorResponse(new Date(), ex.getMessage());
        errorMessage.setDebugMessage("Resource is not found" );
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        ErrorResponse errorMessage = new ErrorResponse(new Date(), ex.getMessage());
        errorMessage.setDebugMessage("User not found");
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {RecordAlreadyExistException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleRecordAlreadyExistException(RecordAlreadyExistException ex, WebRequest request) {
        ErrorResponse errorMessage = new ErrorResponse(new Date(), ex.getMessage());
        errorMessage.setDebugMessage("Record already exist");

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(value = {UnauthorizedUserException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleUnauthorizedUserException(UnauthorizedUserException ex, WebRequest request) {
        ErrorResponse errorMessage = new ErrorResponse(new Date(), ex.getMessage());
        errorMessage.setDebugMessage("You are not authorized to access this endpoint");

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleValidationException(ValidationException ex, WebRequest request) {
        ErrorResponse errorMessage = new ErrorResponse(new Date(), ex.getMessage());
        errorMessage.setDebugMessage("Error: validation failed");

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleOtherServiceException(Exception ex, WebRequest request) {
        ErrorResponse errorMessage = new ErrorResponse(new Date(), ex.getMessage());
        errorMessage.setDebugMessage("An error has occurred.....");

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
