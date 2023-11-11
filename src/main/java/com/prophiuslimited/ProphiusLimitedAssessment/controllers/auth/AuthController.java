package com.prophiuslimited.ProphiusLimitedAssessment.controllers.auth;

import com.prophiuslimited.ProphiusLimitedAssessment.dtos.responses.ApiResponse;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.requests.LoginRequestDto;
import com.prophiuslimited.ProphiusLimitedAssessment.services.UserService;
import com.prophiuslimited.ProphiusLimitedAssessment.utils.ResponseManager;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class AuthController {
    private final UserService userService;
    private final ResponseManager responseManager;

    @Operation(summary = "Login a user",
            description = "Copy the generated token returned in the response object and enter it in the authorization header. \n"
        + "Once authenticated, you can then start performing CRUD operations on protected endpoints. \n")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Object>> login(@RequestBody LoginRequestDto request) {
        return responseManager.success(userService.login(request));
    }

}
