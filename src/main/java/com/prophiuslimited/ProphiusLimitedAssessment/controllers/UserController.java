package com.prophiuslimited.ProphiusLimitedAssessment.controllers;


import com.prophiuslimited.ProphiusLimitedAssessment.dtos.ApiResponse;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.SignupRequestDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.UpdateUserRequestDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.UserResponseDto;
import com.prophiuslimited.ProphiusLimitedAssessment.services.UserService;
import com.prophiuslimited.ProphiusLimitedAssessment.utils.ResponseManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final ResponseManager responseManager;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Object>> createUser(@RequestBody @Valid SignupRequestDto signupRequest) {
        UserResponseDto userResponseDto = userService.signUp(signupRequest);
        return responseManager.success(userResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> getUser(@PathVariable String id) {
        UserResponseDto userResponseDto = userService.getUser(id);
        return responseManager.success(userResponseDto);

    }

    @GetMapping()
    public ResponseEntity<ApiResponse<Object>> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                                        @RequestParam(value = "limit", defaultValue = "5") int limit) {
        List<UserResponseDto> userResponseDtos = userService.getUsers(page, limit);
        return responseManager.success(userResponseDtos);

    }

    @PutMapping("delete/{id}")
    public ResponseEntity<ApiResponse<Object>> updateUser(@PathVariable String id,
                                                          @RequestBody @Valid UpdateUserRequestDto request) {
        UserResponseDto userResponseDto = userService.updateUser(id, request);
        return responseManager.success(userResponseDto);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity <HttpStatus> deleteUser(@PathVariable String id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}