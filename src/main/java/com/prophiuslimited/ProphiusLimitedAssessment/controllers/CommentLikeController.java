package com.prophiuslimited.ProphiusLimitedAssessment.controllers;


import com.prophiuslimited.ProphiusLimitedAssessment.dtos.responses.CommentLikeResponseDto;
import com.prophiuslimited.ProphiusLimitedAssessment.services.CommentLikeService;
import com.prophiuslimited.ProphiusLimitedAssessment.utils.ResponseManager;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;
    private final ResponseManager responseManager;

    @Operation(summary = "Like a specific comment.", description = "If the comment is already liked, its liked status is toggled. \n ")
    @PutMapping("/{userId}/posts/{postId}/comments/{commentId}/comment-like")
    public ResponseEntity<CommentLikeResponseDto> updateComment(@PathVariable String userId, @PathVariable Long postId,
                                                             @PathVariable Long commentId) {

        return new ResponseEntity<>(commentLikeService.updateCommentLike(userId, postId, commentId), HttpStatus.CREATED);

    }
}
