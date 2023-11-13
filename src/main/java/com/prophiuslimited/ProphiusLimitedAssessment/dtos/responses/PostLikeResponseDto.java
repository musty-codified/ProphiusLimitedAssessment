package com.prophiuslimited.ProphiusLimitedAssessment.dtos.responses;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@JsonIgnoreProperties(ignoreUnknown = true)
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
