package com.prophiuslimited.ProphiusLimitedAssessment.controllers;


import com.prophiuslimited.ProphiusLimitedAssessment.utils.ApiResponse;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.requests.SignupRequestDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.requests.UpdateUserRequestDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.responses.UserResponseDto;
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

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<Object>> getUser(@PathVariable String userId) {
        UserResponseDto userResponseDto = userService.getUser(userId);
        return responseManager.success(userResponseDto);

    }

    @GetMapping()
    public ResponseEntity<ApiResponse<Object>> getUsers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "5") int limit,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
                                                        ) {
        List<UserResponseDto> userResponseDtos = userService.getUsers(page, limit, sortBy, sortDir);
        return responseManager.success(userResponseDtos);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<Object>> updateUser(@PathVariable String userId,
                                                          @RequestBody @Valid UpdateUserRequestDto request) {
        UserResponseDto userResponseDto = userService.updateUser(userId, request);
        return responseManager.success(userResponseDto);

    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity <HttpStatus> deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}