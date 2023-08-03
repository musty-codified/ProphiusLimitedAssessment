package com.prophiuslimited.ProphiusLimitedAssessment.entities;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "comment_tbl")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Comment extends BaseEntity{

    @Column(nullable = false)
    private String body;

    private Long postId;
    private Long userId;
    private String username;
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "posts_id", nullable = false)
    private Post post;



}
