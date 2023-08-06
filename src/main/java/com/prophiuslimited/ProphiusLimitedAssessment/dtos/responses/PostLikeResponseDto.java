package com.prophiuslimited.ProphiusLimitedAssessment.dtos.responses;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostLikeResponseDto {

    private Long id;
    private boolean liked;
    private int likesCount;
    private Long postId;
    private String userId;
}
