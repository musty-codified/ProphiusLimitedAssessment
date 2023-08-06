package com.prophiuslimited.ProphiusLimitedAssessment.controllers;


import com.prophiuslimited.ProphiusLimitedAssessment.dtos.requests.PostRequestDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.responses.PostResponseDto;
import com.prophiuslimited.ProphiusLimitedAssessment.services.PostService;
import com.prophiuslimited.ProphiusLimitedAssessment.utils.ApiResponse;
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
public class PostController {

    private final ResponseManager responseManager;
    private final PostService postService;


    @PostMapping("/{userId}/posts")
    public ResponseEntity<ApiResponse<Object>> createPost(@PathVariable String userId,
                                                          @RequestBody @Valid PostRequestDto postRequest) {
        PostResponseDto postResponseDto = postService.createPost(userId, postRequest);

        return responseManager.success(postResponseDto);
    }

    @GetMapping("/{userId}/posts/{postId}")
    public ResponseEntity<ApiResponse<Object>> getPost(@PathVariable String userId, @PathVariable Long postId) {
        PostResponseDto postResponseDto = postService.getPost(userId, postId);
        return responseManager.success(postResponseDto);

    }

    @GetMapping("/{userId}/posts")
    public ResponseEntity<ApiResponse<Object>> getPosts(@PathVariable String userId, @RequestParam(value = "page", defaultValue = "0") int page,
                                                        @RequestParam(value = "limit", defaultValue = "5") int limit,
                                                        @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                                                        @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        List<PostResponseDto> postResponseDtos = postService.getPosts(userId, page, limit, sortBy, sortDir);
        return responseManager.success(postResponseDtos);

    }

    @PutMapping("/{userId}/posts/{postId}")
    public ResponseEntity<ApiResponse<Object>> updatePost(@PathVariable String userId, @PathVariable Long postId,
                                                          @RequestBody @Valid PostRequestDto postRequest) {
        PostResponseDto postResponseDto = postService.updatePost(userId, postId, postRequest);
        return responseManager.success(postResponseDto);

    }

    @DeleteMapping("/{id}/posts/{postId}/delete")
    public ResponseEntity <HttpStatus> deletePost(@PathVariable String id, @PathVariable Long postId){
        postService.deletePost(id, postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
