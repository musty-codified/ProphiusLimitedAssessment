package com.prophiuslimited.ProphiusLimitedAssessment.services;

import com.prophiuslimited.ProphiusLimitedAssessment.dtos.CommentRequestDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.CommentResponseDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.PostRequestDto;

import java.util.List;

public interface CommentService {
    CommentResponseDto createComment(String id, Long postId, CommentRequestDto commentRequest);

    CommentResponseDto getComment(String id, Long postId, Long commentId);

    List<CommentResponseDto> getComments(String id, int cPage, int cLimit);

    CommentResponseDto updateComment(String id, Long postId, Long commentId, CommentRequestDto commentRequest);

    void deleteComment(String id, Long postId, Long commentId);
}
