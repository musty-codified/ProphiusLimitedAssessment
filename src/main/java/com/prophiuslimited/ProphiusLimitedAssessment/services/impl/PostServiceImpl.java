package com.prophiuslimited.ProphiusLimitedAssessment.services.impl;


import com.prophiuslimited.ProphiusLimitedAssessment.controllers.PostController;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.PostRequestDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.PostResponseDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.UserResponseDto;
import com.prophiuslimited.ProphiusLimitedAssessment.entities.Post;
import com.prophiuslimited.ProphiusLimitedAssessment.entities.User;
import com.prophiuslimited.ProphiusLimitedAssessment.exceptions.ResourceNotFoundException;
import com.prophiuslimited.ProphiusLimitedAssessment.exceptions.UnauthorizedUserException;
import com.prophiuslimited.ProphiusLimitedAssessment.exceptions.UserNotFoundException;
import com.prophiuslimited.ProphiusLimitedAssessment.repositories.PostRepository;
import com.prophiuslimited.ProphiusLimitedAssessment.repositories.UserRepository;
import com.prophiuslimited.ProphiusLimitedAssessment.services.PostService;
import com.prophiuslimited.ProphiusLimitedAssessment.utils.AppUtils;
import com.prophiuslimited.ProphiusLimitedAssessment.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Override
    public PostResponseDto createPost(String id, PostRequestDto postRequest) {
        User user = userRepository.findByUserId(id)
                .orElseThrow(()-> new UserNotFoundException("User is not found"));
        Post post = Post.builder()
                .content(postRequest.getContent())
                .user(user)
                .likesCount(0)
                .build();

        Post savedPost = postRepository.save(post);
        logger.info("the post is " + savedPost + " and the userId is " + id);
        return Mapper.toPostDto(savedPost);
    }

    @Override
    public PostResponseDto getPost(String id, Long postId) {
        User user = userRepository.findByUserId(id)
                .orElseThrow(()-> new UserNotFoundException("User with ID " + id + " not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post resource not found"));
//        if (!post.getUser().equals(user))
           Post.builder()
                   .user(user)
                   .content(post.getContent())
                   .likesCount(0)
                   .build();
            return Mapper.toPostDto(post);
    }

    @Override
    public List<PostResponseDto> getPosts(String id, int page, int limit) {

        List<PostResponseDto> returnValue = new ArrayList<>();

        User user = userRepository.findByUserId(id)
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        if(page>0) page = page-1;

        Pageable pageableRequest = PageRequest.of(page,limit);
        Page<Post> postPage = postRepository.findAll(pageableRequest);
        List<Post> posts = postPage.getContent();

        for (Post post : posts){
            Post.builder()
                    .user(user)
                    .content(post.getContent())
                    .likesCount(post.getLikesCount())
                    .build();
            PostResponseDto postResponseDto = Mapper.toPostDto(post);
            returnValue.add(postResponseDto);

        }
        return returnValue;
    }

    @Override
    public PostResponseDto updatePost(String id, Long postId, PostRequestDto postRequest) {

        User user = userRepository.findByUserId(id)
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
    public void deletePost(String id, Long postId) {
        User user = userRepository.findByUserId(id)
                .orElseThrow(()-> new UserNotFoundException("User not found"));
      Post post = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post resource not found"));
      logger.info("User " + user);
      logger.info("Post " + post);
                if (post.getUser().equals(user))
                    postRepository.delete(post);

    }
}
