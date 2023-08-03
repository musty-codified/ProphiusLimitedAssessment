package com.prophiuslimited.ProphiusLimitedAssessment.repositories;

import com.prophiuslimited.ProphiusLimitedAssessment.entities.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByPostId(Long postId, Pageable pageable);
    List<Comment> findAllByPostId(Long postId);
}
