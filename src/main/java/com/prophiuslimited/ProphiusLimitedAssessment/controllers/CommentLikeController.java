package com.prophiuslimited.ProphiusLimitedAssessment.controllers;


import com.prophiuslimited.ProphiusLimitedAssessment.dtos.*;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.responses.CommentLikeResponseDto;
import com.prophiuslimited.ProphiusLimitedAssessment.services.CommentLikeService;
import com.prophiuslimited.ProphiusLimitedAssessment.utils.ResponseManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;
    private final ResponseManager responseManager;
    @PutMapping("/{userId}/posts/{postId}/comments/{commentId}/comment-like")
    public ResponseEntity<ApiResponse<Object>> updateComment(@PathVariable String userId, @PathVariable Long postId,
                                                             @PathVariable Long commentId) {
        CommentLikeResponseDto commentLikeResponseDto = commentLikeService.updateCommentLike(userId, postId, commentId);
        return responseManager.success(commentLikeResponseDto);

    }
}
