package com.prophiuslimited.ProphiusLimitedAssessment.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private String message;
    private boolean status;
    private T payload;
    private HttpStatus statusCode;

    public ApiResponse(String message, boolean status, T payload) {
        this.message = message;
        this.status = status;
        this.payload = payload;
    }

    public ApiResponse(String message, T payload, HttpStatus statusCode) {
        this.message = message;
        this.payload = payload;
        this.statusCode = statusCode;
    }
}
