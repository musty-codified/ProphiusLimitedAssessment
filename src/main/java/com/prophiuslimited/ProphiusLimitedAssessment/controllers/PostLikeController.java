package com.prophiuslimited.ProphiusLimitedAssessment.controllers;


import com.prophiuslimited.ProphiusLimitedAssessment.dtos.requests.SignupRequestDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.responses.UserResponseDto;
import com.prophiuslimited.ProphiusLimitedAssessment.exceptions.ValidationException;
import com.prophiuslimited.ProphiusLimitedAssessment.utils.ApiResponse;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.responses.PostLikeResponseDto;
import com.prophiuslimited.ProphiusLimitedAssessment.services.PostLikeService;
import com.prophiuslimited.ProphiusLimitedAssessment.utils.ResponseManager;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class PostLikeController {
    private final PostLikeService postLikeService;
    private final ResponseManager responseManager;

    @Operation(summary = "Like a specific post. ",
            description = "If the post is already liked, its liked status is toggled. \n  " )

    @PutMapping("/{userId}/posts/{postId}/post-like")
    public ResponseEntity<PostLikeResponseDto> updatePostLike(@PathVariable String userId, @PathVariable Long postId) {
        // Get the current HTTP request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();

        // Get the authenticated userId from the request attribute
        String authenticatedUserId = (String) request.getAttribute("userId");

        // Compare the authenticated userId with the userId from the path variable
        if (!authenticatedUserId.equals(userId)) {
            throw new ValidationException("Invalid user credential.");
        }

        PostLikeResponseDto postLikeResponseDto = postLikeService.updatePostLike(userId, postId);
        return new ResponseEntity<>(postLikeResponseDto, HttpStatus.CREATED);

    }

//    @PostMapping("/signup")
//    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid SignupRequestDto signupRequest){
//        UserResponseDto userResponseDto = userService.signUp(signupRequest);
//        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
//
//    }
}
