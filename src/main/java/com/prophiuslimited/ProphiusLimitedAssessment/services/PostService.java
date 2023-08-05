package com.prophiuslimited.ProphiusLimitedAssessment.services;

import com.prophiuslimited.ProphiusLimitedAssessment.dtos.PostRequestDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.PostResponseDto;

import java.util.List;

public interface PostService {

    PostResponseDto createPost(String userId, PostRequestDto postRequest);

    PostResponseDto getPost(String userId, Long postId);

    List<PostResponseDto> getPosts (String userId, int page, int limit, String sortBy, String sortDir);

    PostResponseDto updatePost (String userId, Long postId, PostRequestDto postRequest);

    void deletePost( String userId, Long postId);
}
