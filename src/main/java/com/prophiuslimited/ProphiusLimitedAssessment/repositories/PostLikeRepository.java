package com.prophiuslimited.ProphiusLimitedAssessment.repositories;

import com.prophiuslimited.ProphiusLimitedAssessment.entities.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

   List<PostLike> findAllByIdAndLiked(Long postId, boolean liked);

   PostLike findAllByUserIdAndId(String id, Long postId);
}
