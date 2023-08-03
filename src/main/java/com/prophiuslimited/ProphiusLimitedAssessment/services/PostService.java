package com.prophiuslimited.ProphiusLimitedAssessment.services;

import com.prophiuslimited.ProphiusLimitedAssessment.dtos.PostRequestDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.PostResponseDto;

import java.util.List;

public interface PostService {

    PostResponseDto createPost(String id, PostRequestDto postRequest);

    PostResponseDto getPost(String id, Long postId);

    List<PostResponseDto> getPosts (String id, int page, int limit);

    PostResponseDto updatePost (String id, Long postId, PostRequestDto postRequest);

    void deletePost( String id, Long postId);
}
