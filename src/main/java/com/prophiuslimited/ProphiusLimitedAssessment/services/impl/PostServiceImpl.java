package com.prophiuslimited.ProphiusLimitedAssessment.services.impl;


import com.prophiuslimited.ProphiusLimitedAssessment.dtos.requests.PostRequestDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.responses.PostResponseDto;
import com.prophiuslimited.ProphiusLimitedAssessment.entities.Post;
import com.prophiuslimited.ProphiusLimitedAssessment.entities.PostLike;
import com.prophiuslimited.ProphiusLimitedAssessment.entities.User;
import com.prophiuslimited.ProphiusLimitedAssessment.exceptions.ResourceNotFoundException;
import com.prophiuslimited.ProphiusLimitedAssessment.exceptions.UnauthorizedUserException;
import com.prophiuslimited.ProphiusLimitedAssessment.exceptions.UserNotFoundException;
import com.prophiuslimited.ProphiusLimitedAssessment.repositories.PostLikeRepository;
import com.prophiuslimited.ProphiusLimitedAssessment.repositories.PostRepository;
import com.prophiuslimited.ProphiusLimitedAssessment.repositories.UserRepository;
import com.prophiuslimited.ProphiusLimitedAssessment.services.PostService;
import com.prophiuslimited.ProphiusLimitedAssessment.utils.AppUtils;
import com.prophiuslimited.ProphiusLimitedAssessment.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final AppUtils appUtil;
    private final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Override
    public PostResponseDto createPost(String userId, PostRequestDto postRequest) {

        User user = userRepository.findByUserId(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found"));
        // Step 1: Create and associate PostLike entities
        Set<PostLike> postLikes = new HashSet<>();

        Post post = Post.builder()
                .content(postRequest.getContent())
                .user(user)
                .likesCount(0)
                .build();
            PostLike postLike = new PostLike();
            postLike.setLiked(false); // Set the default liked status if needed
            postLike.setPost(post);   // Associate the PostLike with the new Post
            postLike.setUserId(userId);
            postLikes.add(postLike);
        System.out.println("User iD is :" + userId);

        logger.info("Post likes: " + postLikes);

        post.setPostLikes(postLikes);
        Post savedPost = postRepository.save(post);
        return appUtil.getMapper().convertValue(savedPost, PostResponseDto.class);
    }

    @Override
    public PostResponseDto getPost(String userId, Long postId) {
        userRepository.findByUserId(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found with ID " + userId ));
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post resource not found"));

         return Mapper.toPostDto(post);
    }

    @Override
    public Page<PostResponseDto> getPosts(String userId, int page, int limit, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();
        userRepository.findByUserId(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found with ID: " + userId));

        if(page>0) page = page-1;

        return postRepository.findAll(PageRequest.of(page, limit, sort))
                .map(Mapper::toPostDto);

    }

    @Override
    public PostResponseDto updatePost(String userId, Long postId, PostRequestDto postRequest) {

        User user = userRepository.findByUserId(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found"));
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post not found"));
        if (!existingPost.getUser().equals(user)) {
            throw new UnauthorizedUserException("You are not authorized to update this post");
        }
        existingPost.setContent(postRequest.getContent());
        existingPost.setUser(user);
        existingPost.setLikesCount(0);
       Post updatedPost = postRepository.save(existingPost);
        return Mapper.toPostDto(updatedPost);
    }

    @Override
    public void deletePost(String userId, Long postId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post resource not found"));
             logger.info("User " + user);
             logger.info("Post " + post);
                if (post.getUser().equals(user))
                    postRepository.delete(post);

    }
}
