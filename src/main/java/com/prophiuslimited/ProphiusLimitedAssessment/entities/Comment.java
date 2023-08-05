package com.prophiuslimited.ProphiusLimitedAssessment.entities;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

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

    private String userId;
    private String username;

    private int likesCount;
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "posts_id", nullable = false)
    private Post post;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private Set<CommentLike> commentLikes;


}
