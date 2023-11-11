package com.prophiuslimited.ProphiusLimitedAssessment.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;


@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "post_like")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PostLike extends BaseEntity{
    private boolean liked;
    private String userId;
    @ManyToOne
    @JoinColumn(name = "posts_id")
    private Post post;


}
