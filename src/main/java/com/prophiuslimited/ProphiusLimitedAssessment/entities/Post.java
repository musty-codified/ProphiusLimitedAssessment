package com.prophiuslimited.ProphiusLimitedAssessment.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "post_tbl")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Post extends BaseEntity{

    @Column(length = 500, nullable = false)
    private String content;

    @Column(nullable = false)
    private int likesCount;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<PostLike> postLikes = new HashSet<>();

}
