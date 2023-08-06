package com.prophiuslimited.ProphiusLimitedAssessment.controllers;


import com.prophiuslimited.ProphiusLimitedAssessment.utils.ApiResponse;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.responses.PostLikeResponseDto;
import com.prophiuslimited.ProphiusLimitedAssessment.services.PostLikeService;
import com.prophiuslimited.ProphiusLimitedAssessment.utils.ResponseManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class PostLikeController {
    private final PostLikeService postLikeService;
    private final ResponseManager responseManager;
    @PutMapping("/{userId}/posts/{postId}/post-like")
    public ResponseEntity<ApiResponse<Object>> updatePostLike(@PathVariable String userId, @PathVariable Long postId) {
        PostLikeResponseDto postLikeResponseDto = postLikeService.updatePostLike(userId, postId);
        return responseManager.success(postLikeResponseDto);

    }
}
