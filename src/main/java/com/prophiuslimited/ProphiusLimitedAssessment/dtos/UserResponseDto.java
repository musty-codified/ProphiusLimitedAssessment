package com.prophiuslimited.ProphiusLimitedAssessment.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {

    private String userId;
    private String email;
    private String userName;
}
