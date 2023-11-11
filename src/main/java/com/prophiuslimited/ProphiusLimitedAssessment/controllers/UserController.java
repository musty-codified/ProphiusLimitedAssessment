package com.prophiuslimited.ProphiusLimitedAssessment.controllers;


import com.prophiuslimited.ProphiusLimitedAssessment.dtos.responses.ApiResponse;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.requests.SignupRequestDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.requests.UpdateUserRequestDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.responses.UserResponseDto;
import com.prophiuslimited.ProphiusLimitedAssessment.services.UserService;
import com.prophiuslimited.ProphiusLimitedAssessment.utils.ResponseManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "User Endpoint", description = "<h3>Operation pertaining to users: </h3> " +
        "<ol>" +
        "<li>Go to '/signup' endpoint to create a new user account.</li> " +
        "<li>Proceed to '/login' endpoint to login. Copy the token returned if signup was successful.</li>" +
        "<li> Append 'Bearer ' prefix to the token before pasting it in the 'Authorization' header. </li>" +
        "<li> Armed with this token, you can now start accessing protected routes. </li>" +
        "</ol> " )
public class UserController {
    private final ResponseManager responseManager;
    private final UserService userService;

    @Operation(summary = "Create a new user account",
            description = "After creating your account, Proceed to login. \n")
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid SignupRequestDto signupRequest){
        return new ResponseEntity<>(userService.signUp(signupRequest), HttpStatus.CREATED);

    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<Object>> getUser(@PathVariable String userId) {
        return responseManager.success(userService.getUser(userId));
    }

    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Fetches list of users",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserController.class)) }),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Users not found",
                    content = @Content) })
    @GetMapping()
    public ResponseEntity<ApiResponse<Object>> getUsers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "5") int limit,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        return responseManager.success(userService.getUsers(page, limit, sortBy, sortDir));
    }
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<Object>> updateUser(@PathVariable String userId, @Valid @RequestBody UpdateUserRequestDto request) {
        return responseManager.success(userService.updateUser(userId, request));

    }
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity <HttpStatus> deleteUser( @Parameter(description = "id of user to be deleted") @PathVariable String userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}