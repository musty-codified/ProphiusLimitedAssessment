package com.prophiuslimited.ProphiusLimitedAssessment.services.impl;

import com.prophiuslimited.ProphiusLimitedAssessment.dtos.responses.PostLikeResponseDto;
import com.prophiuslimited.ProphiusLimitedAssessment.entities.Post;
import com.prophiuslimited.ProphiusLimitedAssessment.entities.PostLike;
import com.prophiuslimited.ProphiusLimitedAssessment.entities.User;
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

import java.util.Optional;


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
                .orElseThrow(()-> new UserNotFoundException("User not found with ID: " + userId));
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post not found with ID: " + postId));

        PostLike postLike = postLikeRepository.findAllByUserIdAndPostId(userId, postId);
        logger.info("postLike " + postLike);

        if (postLike == null) {
            // If the postLike doesn't exist, create a new one and set its properties
            postLike = new PostLike();
            postLike.setLiked(true);
            postLike.setUserId(userId);
            postLike.setPost(post);
            postLikeRepository.save(postLike);

            //Increment the likesCount of the Associated Post entity
            post.setLikesCount(post.getLikesCount() + 1);
        } else {
            // If the postLike exists, toggle its liked status
            boolean liked = postLike.isLiked();
            postLike.setLiked(!liked);
            postLikeRepository.save(postLike);

            if (liked){
                post.setLikesCount(post.getLikesCount() - 1);
            } else {
                post.setLikesCount(post.getLikesCount() + 1);
            }
        }
        postRepository.save(post);

        return Mapper.toPostLikeDto(postLike);

    }

//        User user = userRepository.findByUserId(userId)
//                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
//
//        Post post = postRepository.findById(postId)
//                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + postId));
//
//        // Check if the user has already liked the post
//        Optional<PostLike> existingPostLike = post.getPostLikes().stream()
//                .filter(like -> like.getUserId().equals(userId))
//                .findFirst();
//
//        if (existingPostLike.isPresent()) {
//            // User has already liked the post, so toggle the like status
//            PostLike postLike = existingPostLike.get();
//            boolean liked = postLike.isLiked();
//            postLike.setLiked(!liked);
//            postLikeRepository.save(postLike);
//
//            // Update the likesCount of the Post entity
//            if (liked) {
//                post.setLikesCount(post.getLikesCount() - 1);
//            } else {
//                post.setLikesCount(post.getLikesCount() + 1);
//            }
//        } else {
//            // User hasn't liked the post yet, so create a new PostLike
//            PostLike newPostLike = new PostLike();
//            newPostLike.setLiked(true);
//            newPostLike.setUserId(userId);
//            newPostLike.setPost(post);
//            postLikeRepository.save(newPostLike);
//
//            // Increment the likesCount of the Post entity
//            post.setLikesCount(post.getLikesCount() + 1);
//        }
//
//        // Save the updated Post entity
//        postRepository.save(post);
//
//        return Mapper.toPostLikeDto(updatedPostLike);
//    }
}

