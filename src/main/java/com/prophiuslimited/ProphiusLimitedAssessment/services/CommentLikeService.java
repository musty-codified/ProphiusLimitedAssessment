package com.prophiuslimited.ProphiusLimitedAssessment.services;

import com.prophiuslimited.ProphiusLimitedAssessment.dtos.CommentLikeResponseDto;

public interface CommentLikeService {
    CommentLikeResponseDto updateCommentLike(String userId, Long postId, Long commentId);
}
