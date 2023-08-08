package com.prophiuslimited.ProphiusLimitedAssessment.entities;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "comment_like")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CommentLike extends BaseEntity{

    private boolean liked;
    private int likeCount;
    private Long postId;
    private String userId;
    @ManyToOne
    @JoinColumn(name = "comments_id")
    private Comment comment;
}
