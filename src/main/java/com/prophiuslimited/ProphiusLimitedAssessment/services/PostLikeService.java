package com.prophiuslimited.ProphiusLimitedAssessment.services;

import com.prophiuslimited.ProphiusLimitedAssessment.dtos.PostLikeResponseDto;

public interface PostLikeService {
    PostLikeResponseDto updatePostLike(String userId, Long postId);
}
