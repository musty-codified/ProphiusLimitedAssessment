package com.prophiuslimited.ProphiusLimitedAssessment.entities;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "comment_tbl")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Comment extends BaseEntity{

    private String commentId;
    @Column(name = "commenter_name", nullable = false)
    private String commenterName;

    @Column(name = "comment_content", nullable = false)
    private String commentContent;
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
