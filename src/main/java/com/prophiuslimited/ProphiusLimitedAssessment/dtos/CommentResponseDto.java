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
public class CommentResponseDto {
    private String body;

    private String userId;

    private Long postId;

    private String username;

    private List<CommentLikeResponseDto> commentLikeResponses;

}
