package com.prophiuslimited.ProphiusLimitedAssessment.services;

import com.prophiuslimited.ProphiusLimitedAssessment.dtos.requests.LoginRequestDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.requests.SignupRequestDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.requests.UpdateUserRequestDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.responses.LoginResponseDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.responses.UserResponseDto;
import org.springframework.data.domain.Page;


public interface UserService {
    UserResponseDto signUp(SignupRequestDto signupRequest);
    UserResponseDto getUser(String userId);
    Page<UserResponseDto> getUsers(int page, int limit, String sortBy, String sortDir);
    UserResponseDto updateUser(String userId, UpdateUserRequestDto updateRequestDto);
    void deleteUser(String userId);
    LoginResponseDto login(LoginRequestDto request);
}
