package com.prophiuslimited.ProphiusLimitedAssessment.services;

import com.prophiuslimited.ProphiusLimitedAssessment.dtos.SignupRequestDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.UpdateUserRequestDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto signUp(SignupRequestDto signupRequest);
    UserResponseDto getUser(String id);

    List<UserResponseDto> getUsers(int page, int limit);

    UserResponseDto updateUser(String id, UpdateUserRequestDto updateRequestDto);
    void deleteUser(String id);

}
