package com.prophiuslimited.ProphiusLimitedAssessment.services.impl;

import com.prophiuslimited.ProphiusLimitedAssessment.dtos.responses.PostLikeResponseDto;
import com.prophiuslimited.ProphiusLimitedAssessment.entities.Post;
import com.prophiuslimited.ProphiusLimitedAssessment.entities.PostLike;
import com.prophiuslimited.ProphiusLimitedAssessment.exceptions.ResourceNotFoundException;
import com.prophiuslimited.ProphiusLimitedAssessment.exceptions.UserNotFoundException;
import com.prophiuslimited.ProphiusLimitedAssessment.repositories.PostLikeRepository;
import com.prophiuslimited.ProphiusLimitedAssessment.repositories.PostRepository;
import com.prophiuslimited.ProphiusLimitedAssessment.repositories.UserRepository;
import com.prophiuslimited.ProphiusLimitedAssessment.services.PostLikeService;
import com.prophiuslimited.ProphiusLimitedAssessment.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PostLikeServiceImpl implements PostLikeService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    private final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Override
    public PostLikeResponseDto updatePostLike(String userId, Long postId) {

        userRepository.findByUserId(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post not found"));

        PostLike postLike = postLikeRepository.findAllByUserIdAndId(userId, postId);
        logger.info("postLike " + postLike);

        if (postLike == null) {
            // If the postLike doesn't exist, create a new one and set its properties
            postLike = new PostLike();
            postLike.setLiked(true);
            postLike.setUserId(userId);
            postLike.setPost(post);
            postLikeRepository.save(postLike);

            //Increment the likesCount of the Post entity
            post.setLikesCount(post.getLikesCount() + 1);
            postLike.getPost().setLikesCount(post.getLikesCount() + 1);
        } else {
            // If the postLike exists, toggle its liked status
            boolean liked = postLike.isLiked();
            postLike.setLiked(!liked);
            postLikeRepository.save(postLike);

            int likesCount = post.getLikesCount();
            post.setLikesCount(liked ? likesCount - 1 : likesCount + 1);
        }

        PostLike updatedPostLike = postLikeRepository.save(postLike);

        return Mapper.toPostLikeDto(updatedPostLike);
    }
}

