package com.prophiuslimited.ProphiusLimitedAssessment.controllers;


import com.prophiuslimited.ProphiusLimitedAssessment.exceptions.ValidationException;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.responses.PostLikeResponseDto;
import com.prophiuslimited.ProphiusLimitedAssessment.services.PostLikeService;
import com.prophiuslimited.ProphiusLimitedAssessment.utils.ResponseManager;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class PostLikeController {
    private final PostLikeService postLikeService;
    private final ResponseManager responseManager;

    @Operation(summary = "Like a specific post. ",
            description = "If the post is already liked, its liked status is toggled. \n  " )

    @PutMapping("/{userId}/posts/{postId}/post-like")
    public ResponseEntity<PostLikeResponseDto> updatePostLike(@PathVariable String userId, @PathVariable Long postId) {

        return new ResponseEntity<>(postLikeService.updatePostLike(userId, postId), HttpStatus.CREATED);

    }

}
