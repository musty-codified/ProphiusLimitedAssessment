package com.prophiuslimited.ProphiusLimitedAssessment.services;

import com.prophiuslimited.ProphiusLimitedAssessment.dtos.requests.CommentRequestDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.responses.CommentResponseDto;

import java.util.List;

public interface CommentService {
    CommentResponseDto createComment(String userId, Long postId, CommentRequestDto commentRequest);

    CommentResponseDto getComment(String userId, Long postId, Long commentId);

    List<CommentResponseDto> getComments(String userId, Long postId,  int cPage, int cLimit, String sortBy, String sortDir);

    CommentResponseDto updateComment(String userId, Long postId, Long commentId, CommentRequestDto commentRequest);

    void deleteComment(String userId, Long postId, Long commentId);
}
