package com.prophiuslimited.ProphiusLimitedAssessment.repositories;

import com.prophiuslimited.ProphiusLimitedAssessment.entities.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    CommentLike findAllByCommentIdAndPostIdAndUserId(Long commentId, Long postId, String userId);
    CommentLike findByCommentIdAndUserId(Long commentId, String userId);

}
