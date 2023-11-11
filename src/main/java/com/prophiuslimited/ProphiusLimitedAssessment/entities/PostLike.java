package com.prophiuslimited.ProphiusLimitedAssessment.entities;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "post_like")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PostLike extends BaseEntity{
    private boolean liked;
    //this refers to liker of the post
    private String userId;
    @ManyToOne
    @JoinColumn(name = "posts_id")
    private Post post;


}
