package com.prophiuslimited.ProphiusLimitedAssessment.controllers;


import com.prophiuslimited.ProphiusLimitedAssessment.dtos.*;
import com.prophiuslimited.ProphiusLimitedAssessment.services.PostService;
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


    @PostMapping("/{id}/posts")
    public ResponseEntity<ApiResponse<Object>> createPost( @PathVariable String id,
                                                           @RequestBody @Valid PostRequestDto postRequest) {
        PostResponseDto postResponseDto = postService.createPost(id, postRequest);

        return responseManager.success(postResponseDto);
    }

    @GetMapping("/{id}/posts/{postId}")
    public ResponseEntity<ApiResponse<Object>> getPost(@PathVariable String id, @PathVariable Long postId) {
        PostResponseDto postResponseDto = postService.getPost(id, postId);
        return responseManager.success(postResponseDto);

    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<ApiResponse<Object>> getPosts(@PathVariable String id, @RequestParam(value = "page", defaultValue = "0") int page,
                                                        @RequestParam(value = "limit", defaultValue = "5") int limit) {
        List<PostResponseDto> postResponseDtos = postService.getPosts(id, page, limit);
        return responseManager.success(postResponseDtos);

    }

    @PutMapping("/{id}/posts/{postId}")
    public ResponseEntity<ApiResponse<Object>> updatePost(@PathVariable String id, @PathVariable Long postId,
                                                          @RequestBody @Valid PostRequestDto postRequest) {
        PostResponseDto postResponseDto = postService.updatePost(id, postId, postRequest);
        return responseManager.success(postResponseDto);

    }

    @DeleteMapping("/{id}/posts/{postId}/delete")
    public ResponseEntity <HttpStatus> deletePost(@PathVariable String id, @PathVariable Long postId){
        postService.deletePost(id, postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
