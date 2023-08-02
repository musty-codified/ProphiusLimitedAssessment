package com.prophiuslimited.ProphiusLimitedAssessment.utils;


import com.prophiuslimited.ProphiusLimitedAssessment.dtos.UserResponseDto;
import com.prophiuslimited.ProphiusLimitedAssessment.entities.User;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public static UserResponseDto toUserDto(User user) {
        return UserResponseDto.builder()
                .userId(user.getUserId())
                .userName(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
