package com.prophiuslimited.ProphiusLimitedAssessment.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {

    private String email;
    private String password;
}