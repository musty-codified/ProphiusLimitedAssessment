package com.prophiuslimited.ProphiusLimitedAssessment.dtos.requests;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {

    private String body;

    private String userId;

    private Long postId;

    private String username;

}
