package com.prophiuslimited.ProphiusLimitedAssessment.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponseDto {

    private String userId;
    private String content;
    private String username;
    private List<PostLikeResponseDto> postLikeResponses = new ArrayList<>();
    private List<CommentResponseDto> commentResponses = new ArrayList<>();
}
