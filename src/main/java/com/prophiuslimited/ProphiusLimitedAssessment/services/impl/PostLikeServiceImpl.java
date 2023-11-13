package com.prophiuslimited.ProphiusLimitedAssessment.services.impl;

import com.prophiuslimited.ProphiusLimitedAssessment.dtos.responses.PostLikeResponseDto;
import com.prophiuslimited.ProphiusLimitedAssessment.entities.Post;
import com.prophiuslimited.ProphiusLimitedAssessment.entities.PostLike;
import com.prophiuslimited.ProphiusLimitedAssessment.exceptions.ResourceNotFoundException;
import com.prophiuslimited.ProphiusLimitedAssessment.exceptions.UserNotFoundException;
import com.prophiuslimited.ProphiusLimitedAssessment.repositories.PostLikeRepository;
import com.prophiuslimited.ProphiusLimitedAssessment.repositories.PostRepository;
import com.prophiuslimited.ProphiusLimitedAssessment.repositories.UserRepository;
import com.prophiuslimited.ProphiusLimitedAssessment.security.JwtUtils;
import com.prophiuslimited.ProphiusLimitedAssessment.services.PostLikeService;
import com.prophiuslimited.ProphiusLimitedAssessment.utils.AppUtils;
import com.prophiuslimited.ProphiusLimitedAssessment.utils.Mapper;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class PostLikeServiceImpl implements PostLikeService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    private final AppUtils appUtil;

    private final JwtUtils jwtUtil;
    private Map<Integer, PostLike> postLikeMap = new HashMap<>();

    private final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Override
    public PostLikeResponseDto updatePostLike(String userId, Long postId) {

        userRepository.findByUserId(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found with ID: " + userId));
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post not found "));

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
            System.out.println(liked);
            postLike.setLiked(!liked);
            postLikeRepository.save(postLike);

            if (liked){
                post.setLikesCount(post.getLikesCount() - 1);
            } else {
                post.setLikesCount(post.getLikesCount() + 1);
            }
        }
        postRepository.save(post);
      PostLikeResponseDto postLikeResponseDto = appUtil.getMapper().convertValue(postLike, PostLikeResponseDto.class);
      postLikeResponseDto.setPostId(postId);
      postLikeResponseDto.setLikesCount(post.getLikesCount());
        return postLikeResponseDto;

    }

}

