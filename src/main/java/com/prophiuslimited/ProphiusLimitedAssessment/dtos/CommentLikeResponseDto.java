package com.prophiuslimited.ProphiusLimitedAssessment.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentLikeResponseDto {

    private Long id;
    private boolean liked;
    private int likesCount;
    private Long postId;
    private String userId;
}
