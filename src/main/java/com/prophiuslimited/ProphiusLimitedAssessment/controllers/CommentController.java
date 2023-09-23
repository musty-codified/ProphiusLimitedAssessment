package com.prophiuslimited.ProphiusLimitedAssessment.controllers;


import com.prophiuslimited.ProphiusLimitedAssessment.dtos.requests.CommentRequestDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.requests.SignupRequestDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.responses.CommentResponseDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.responses.UserResponseDto;
import com.prophiuslimited.ProphiusLimitedAssessment.exceptions.ValidationException;
import com.prophiuslimited.ProphiusLimitedAssessment.services.CommentService;
import com.prophiuslimited.ProphiusLimitedAssessment.utils.ApiResponse;
import com.prophiuslimited.ProphiusLimitedAssessment.utils.ResponseManager;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
public class CommentController {
    private final ResponseManager responseManager;
    private final CommentService commentService;

    @Operation(summary = "Create a new comment for a post ")
    @PostMapping("/{userId}/posts/{postId}/comments")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable String userId, @PathVariable Long postId,
                                                         @RequestBody @Valid CommentRequestDto commentRequest){

        // Get the current HTTP request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();

        // Get the authenticated userId from the request attribute
        String authenticatedUserId = (String) request.getAttribute("userId");

        // Compare the authenticated userId with the userId from the path variable
        if (!authenticatedUserId.equals(userId)) {
            throw new ValidationException("Invalid userId.");
        }
        CommentResponseDto commentResponseDto = commentService.createComment(userId, postId, commentRequest);
        return new ResponseEntity<>(commentResponseDto, HttpStatus.CREATED);

    }

    @Operation(summary = "Retrieve the details of a comment")
    @GetMapping("/{userId}/posts/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<Object>> getComment(@PathVariable String userId, @PathVariable Long postId,
                                                          @PathVariable Long commentId) {

        // Get the current HTTP request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();

        // Get the authenticated userId from the request attribute
        String authenticatedUserId = (String) request.getAttribute("userId");

        // Compare the authenticated userId with the userId from the path variable
        if (!authenticatedUserId.equals(userId)) {
            throw new ValidationException("Invalid userId.");
        }
        CommentResponseDto commentResponseDto = commentService.getComment(userId, postId, commentId);
        return responseManager.success(commentResponseDto);

    }

    @Operation(summary = "Retrieve all the comments for a specific post")
    @GetMapping("/{userId}/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<Object>> getComments(@PathVariable String userId, @PathVariable Long postId,
                                                           @RequestParam(value = "page", defaultValue = "0") int cPage,
                                                           @RequestParam(value = "limit", defaultValue = "5") int cLimit,
                                                           @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                                                           @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        // Get the current HTTP request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();

        // Get the authenticated userId from the request attribute
        String authenticatedUserId = (String) request.getAttribute("userId");

        // Compare the authenticated userId with the userId from the path variable
        if (!authenticatedUserId.equals(userId)) {
            throw new ValidationException("Invalid userId.");
        }
        Page<CommentResponseDto> commentResponseDtos = commentService.getComments(userId, postId, cPage, cLimit, sortBy, sortDir);
        return responseManager.success(commentResponseDtos);

    }
    @Operation(summary = "Update a specific comment ")
    @PutMapping("/{userId}/posts/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<Object>> updateComment(@PathVariable String userId, @PathVariable Long postId,
                                                             @PathVariable Long commentId, @RequestBody @Valid CommentRequestDto commentRequest) {

        // Get the current HTTP request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();

        // Get the authenticated userId from the request attribute
        String authenticatedUserId = (String) request.getAttribute("userId");

        // Compare the authenticated userId with the userId from the path variable
        if (!authenticatedUserId.equals(userId)) {
            throw new ValidationException("Invalid userId.");
        }
        CommentResponseDto commentResponseDto = commentService.updateComment(userId, postId,commentId, commentRequest);
        return responseManager.success(commentResponseDto);

    }
    @Operation(summary = "Delete a specific comment for a post")
    @DeleteMapping("/{userId}/posts/{postId}/comments/{commentId}/delete")
    public ResponseEntity <HttpStatus> deleteComment(@PathVariable String userId, @PathVariable Long postId,
                                                     @PathVariable Long commentId){

        // Get the current HTTP request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();

        // Get the authenticated userId from the request attribute
        String authenticatedUserId = (String) request.getAttribute("userId");

        // Compare the authenticated userId with the userId from the path variable
        if (!authenticatedUserId.equals(userId)) {
            throw new ValidationException("Invalid userId.");
        }
        commentService.deleteComment(userId, postId, commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
