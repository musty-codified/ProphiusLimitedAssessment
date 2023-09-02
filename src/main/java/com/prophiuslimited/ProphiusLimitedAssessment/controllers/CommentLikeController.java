package com.prophiuslimited.ProphiusLimitedAssessment.controllers;


import com.prophiuslimited.ProphiusLimitedAssessment.dtos.responses.CommentLikeResponseDto;
import com.prophiuslimited.ProphiusLimitedAssessment.exceptions.ValidationException;
import com.prophiuslimited.ProphiusLimitedAssessment.services.CommentLikeService;
import com.prophiuslimited.ProphiusLimitedAssessment.utils.ApiResponse;
import com.prophiuslimited.ProphiusLimitedAssessment.utils.ResponseManager;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;
    private final ResponseManager responseManager;

    @Operation(summary = "Like a specific comment.",
            description = "If the comment is already liked, its liked status is toggled. \n ")
    @PutMapping("/{userId}/posts/{postId}/comments/{commentId}/comment-like")
    public ResponseEntity<ApiResponse<Object>> updateComment(@PathVariable String userId, @PathVariable Long postId,
                                                             @PathVariable Long commentId) {

        // Get the current HTTP request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();

        // Get the authenticated userId from the request attribute
        String authenticatedUserId = (String) request.getAttribute("userId");

        // Compare the authenticated userId with the userId from the path variable
        if (!authenticatedUserId.equals(userId)) {
            throw new ValidationException("You are required to authenticate to like this comment.");
        }
        CommentLikeResponseDto commentLikeResponseDto = commentLikeService.updateCommentLike(userId, postId, commentId);
        return responseManager.success(commentLikeResponseDto);

    }
}
