package com.prophiuslimited.ProphiusLimitedAssessment.controllers;


import com.prophiuslimited.ProphiusLimitedAssessment.dtos.*;
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


    @PostMapping("/{id}/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<Object>> createPost(@PathVariable String id, @PathVariable Long postId,
                                                          @RequestBody @Valid CommentRequestDto commentRequest) {
        CommentResponseDto commentResponseDto = commentService.createComment(id, postId, commentRequest);

        return responseManager.success(commentResponseDto);
    }

    @GetMapping("/{id}/posts/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<Object>> getComment(@PathVariable String id, @PathVariable Long postId,
                                                          @PathVariable Long commentId) {
        CommentResponseDto commentResponseDto = commentService.getComment(id, postId, commentId);
        return responseManager.success(commentResponseDto);

    }

    @GetMapping("/{id}/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<Object>> getComments(@PathVariable String id, @PathVariable Long postId,
                                                           @RequestParam(value = "page", defaultValue = "0") int cPage,
                                                        @RequestParam(value = "limit", defaultValue = "5") int cLimit) {
        List<CommentResponseDto> commentResponseDtos = commentService.getComments(id, cPage, cLimit);
        return responseManager.success(commentResponseDtos);

    }

    @PutMapping("/{id}/posts/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<Object>> updateComment(@PathVariable String id, @PathVariable Long postId,
                                                             @PathVariable Long commentId, @RequestBody @Valid CommentRequestDto commentRequest) {
        CommentResponseDto commentResponseDto = commentService.updateComment(id, postId,commentId, commentRequest);
        return responseManager.success(commentResponseDto);

    }

    @DeleteMapping("/{id}/posts/{postId}/comments/{commentId}/delete")
    public ResponseEntity <HttpStatus> deleteComment(@PathVariable String id, @PathVariable Long postId,
                                                  @PathVariable Long commentId){
        commentService.deleteComment(id, postId, commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
