package com.prophiuslimited.ProphiusLimitedAssessment.controllers;

import com.prophiuslimited.ProphiusLimitedAssessment.dtos.requests.PostRequestDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.responses.PostResponseDto;
import com.prophiuslimited.ProphiusLimitedAssessment.exceptions.ValidationException;
import com.prophiuslimited.ProphiusLimitedAssessment.services.PostService;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.responses.ApiResponse;
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
public class PostController {

    private final ResponseManager responseManager;
    private final PostService postService;

    @Operation(summary = "Create a new post for a specific user")
    @PostMapping("/{userId}/posts")
    public ResponseEntity<PostResponseDto> createPost(@PathVariable String userId, @Valid @RequestBody PostRequestDto postRequest){

        // Get the current HTTP request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();

        // Get the authenticated userId from the request attribute
        String authenticatedUserId = (String) request.getAttribute("userId");

        // Compare the authenticated userId with the userId from the path variable
        if (!authenticatedUserId.equals(userId)) {
            throw new ValidationException("Invalid userId.");
        }
        return new ResponseEntity<>(postService.createPost(userId, postRequest), HttpStatus.CREATED);

    }
    @Operation(summary = "Retrieve the details of a specific post")
    @GetMapping("/{userId}/posts/{postId}")
    public ResponseEntity<ApiResponse<Object>> getPost(@PathVariable String userId, @PathVariable Long postId) {

        // Get the current HTTP request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();

        // Get the authenticated userId from the request attribute
        String authenticatedUserId = (String) request.getAttribute("userId");

        // Compare the authenticated userId with the userId from the path variable
        if (!authenticatedUserId.equals(userId)) {
            throw new ValidationException("Invalid userId.");
        }
        return responseManager.success(postService.getPost(userId, postId));

    }
    @Operation(summary = "Retrieve all posts for a specific user")
    @GetMapping("/{userId}/posts")
    public ResponseEntity<ApiResponse<Object>> getPosts(@PathVariable String userId, @RequestParam(value = "page", defaultValue = "0") int page,
                                                        @RequestParam(value = "limit", defaultValue = "5") int limit,
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
        return responseManager.success(postService.getPosts(userId, page, limit, sortBy, sortDir));

    }

    @Operation(summary = "Update a specific post")
    @PutMapping("/{userId}/posts/{postId}")
    public ResponseEntity<ApiResponse<Object>> updatePost(@PathVariable String userId, @PathVariable Long postId,
                                                          @Valid @RequestBody PostRequestDto postRequest) {
        // Get the current HTTP request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();

        // Get the authenticated userId from the request attribute
        String authenticatedUserId = (String) request.getAttribute("userId");

        // Compare the authenticated userId with the userId from the path variable
        if (!authenticatedUserId.equals(userId)) {
            throw new ValidationException("Invalid userId.");
        }
        return responseManager.success(postService.updatePost(userId, postId, postRequest));

    }


    @Operation(summary = "Delete a specific post for a user")
    @DeleteMapping("/{userId}/posts/{postId}/delete")
    public ResponseEntity <HttpStatus> deletePost(@PathVariable String userId, @PathVariable Long postId){

        // Get the current HTTP request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();

        // Get the authenticated userId from the request attribute
        String authenticatedUserId = (String) request.getAttribute("userId");

        // Compare the authenticated userId with the userId from the path variable
        if (!authenticatedUserId.equals(userId)) {
            throw new ValidationException("Invalid userId.");
        }
        postService.deletePost(userId, postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
