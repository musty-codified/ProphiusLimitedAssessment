package com.prophiuslimited.ProphiusLimitedAssessment.repositories;

import com.prophiuslimited.ProphiusLimitedAssessment.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findById(Long postId);
}
