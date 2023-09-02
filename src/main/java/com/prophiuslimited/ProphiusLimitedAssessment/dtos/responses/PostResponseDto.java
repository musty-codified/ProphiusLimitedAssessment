package com.prophiuslimited.ProphiusLimitedAssessment.dtos.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    private List<PostLikeResponseDto> postLikeResponses = new ArrayList<>();
    @JsonIgnore
    private List<CommentResponseDto> commentResponses = new ArrayList<>();
}
