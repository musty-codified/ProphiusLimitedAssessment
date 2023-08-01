package com.prophiuslimited.ProphiusLimitedAssessment.entities;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "post_tbl")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Post extends BaseEntity{


    @Column(length = 50, nullable = false)
    private String author;

    @Column(length = 500, nullable = false)
    private String postContent;

    private int likesCount;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
