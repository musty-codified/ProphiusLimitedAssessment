package com.prophiuslimited.ProphiusLimitedAssessment.controllers;


import com.prophiuslimited.ProphiusLimitedAssessment.dtos.*;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.requests.CommentRequestDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.responses.CommentResponseDto;
import com.prophiuslimited.ProphiusLimitedAssessment.services.CommentService;
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
public class CommentController {
    private final ResponseManager responseManager;
    private final CommentService commentService;

    @PostMapping("/{userId}/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<Object>> createComment(@PathVariable String userId, @PathVariable Long postId,
                                                             @RequestBody @Valid CommentRequestDto commentRequest) {
        CommentResponseDto commentResponseDto = commentService.createComment(userId, postId, commentRequest);
        return responseManager.success(commentResponseDto);
    }

    @GetMapping("/{userId}/posts/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<Object>> getComment(@PathVariable String userId, @PathVariable Long postId,
                                                          @PathVariable Long commentId) {
        CommentResponseDto commentResponseDto = commentService.getComment(userId, postId, commentId);
        return responseManager.success(commentResponseDto);

    }

    @GetMapping("/{userId}/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<Object>> getComments(@PathVariable String userId, @PathVariable Long postId,
                                                           @RequestParam(value = "page", defaultValue = "0") int cPage,
                                                           @RequestParam(value = "limit", defaultValue = "5") int cLimit,
                                                           @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                                                           @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        List<CommentResponseDto> commentResponseDtos = commentService.getComments(userId, postId, cPage, cLimit, sortBy, sortDir);
        return responseManager.success(commentResponseDtos);

    }

    @PutMapping("/{userId}/posts/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<Object>> updateComment(@PathVariable String userId, @PathVariable Long postId,
                                                             @PathVariable Long commentId, @RequestBody @Valid CommentRequestDto commentRequest) {
        CommentResponseDto commentResponseDto = commentService.updateComment(userId, postId,commentId, commentRequest);
        return responseManager.success(commentResponseDto);

    }

    @DeleteMapping("/{userId}/posts/{postId}/comments/{commentId}/delete")
    public ResponseEntity <HttpStatus> deleteComment(@PathVariable String userId, @PathVariable Long postId,
                                                     @PathVariable Long commentId){
        commentService.deleteComment(userId, postId, commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
