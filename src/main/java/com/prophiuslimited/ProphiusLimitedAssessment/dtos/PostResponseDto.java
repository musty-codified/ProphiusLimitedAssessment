package com.prophiuslimited.ProphiusLimitedAssessment.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponseDto {

    private String userId;
    private String content;
    private int likesCount;
    private String username;
//    private List<PostLikeResponse> postLikeResponses;
//    private List<CommentResponse> commentResponses;
}
