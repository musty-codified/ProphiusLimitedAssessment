package com.prophiuslimited.ProphiusLimitedAssessment.services;

import com.prophiuslimited.ProphiusLimitedAssessment.dtos.requests.PostRequestDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.responses.PostResponseDto;
import org.springframework.data.domain.Page;

public interface PostService {

    PostResponseDto createPost(String userId, PostRequestDto postRequest);

    PostResponseDto getPost(String userId, Long postId);

    Page<PostResponseDto> getPosts (String userId, int page, int limit, String sortBy, String sortDir);

    PostResponseDto updatePost (String userId, Long postId, PostRequestDto postRequest);

    void deletePost( String userId, Long postId);
}
