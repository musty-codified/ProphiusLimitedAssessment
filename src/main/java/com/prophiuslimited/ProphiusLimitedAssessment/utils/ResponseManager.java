package com.prophiuslimited.ProphiusLimitedAssessment.utils;


import com.prophiuslimited.ProphiusLimitedAssessment.dtos.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ResponseManager {

    public ResponseEntity<ApiResponse<Object>> success(Object payload){
        return ResponseEntity.ok().body(
                new ApiResponse<>("Request successful", true, payload)
        );
    }
}
