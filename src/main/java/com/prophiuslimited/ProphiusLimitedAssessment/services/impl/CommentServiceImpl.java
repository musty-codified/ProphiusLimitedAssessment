package com.prophiuslimited.ProphiusLimitedAssessment.services.impl;


import com.prophiuslimited.ProphiusLimitedAssessment.dtos.CommentRequestDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.CommentResponseDto;
import com.prophiuslimited.ProphiusLimitedAssessment.entities.Comment;
import com.prophiuslimited.ProphiusLimitedAssessment.entities.Post;
import com.prophiuslimited.ProphiusLimitedAssessment.entities.User;
import com.prophiuslimited.ProphiusLimitedAssessment.exceptions.ResourceNotFoundException;
import com.prophiuslimited.ProphiusLimitedAssessment.exceptions.UnauthorizedUserException;
import com.prophiuslimited.ProphiusLimitedAssessment.exceptions.UserNotFoundException;
import com.prophiuslimited.ProphiusLimitedAssessment.repositories.CommentRepository;
import com.prophiuslimited.ProphiusLimitedAssessment.repositories.PostRepository;
import com.prophiuslimited.ProphiusLimitedAssessment.repositories.UserRepository;
import com.prophiuslimited.ProphiusLimitedAssessment.services.CommentService;
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
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Override
    public CommentResponseDto createComment(String id, Long postId, CommentRequestDto commentRequest) {
        userRepository.findByUserId(id).orElseThrow(()-> new UserNotFoundException("User not found"));
       if (!postRepository.existsById(postId)) throw new ResourceNotFoundException("Post resource not found");

       Comment comment = Comment.builder()
               .body(commentRequest.getBody())
               .userId(commentRequest.getUserId())
               .username(commentRequest.getUsername())
               .post(postRepository.findById(postId).get())
               .build();

      Comment savedComment = commentRepository.save(comment);

        return Mapper.toCommentDto(savedComment);
    }

    @Override
    public CommentResponseDto getComment(String id, Long postId, Long commentId) {
        userRepository.findByUserId(id)
                .orElseThrow(()-> new UserNotFoundException("User not found"));
        postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post resource not found"));
      Comment comment =  commentRepository.
                findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment not found"));
        Comment.builder()
                .body(comment.getBody())
                .post(comment.getPost())
                .username(comment.getUsername())
                .userId(comment.getUserId())
                .build();
        return Mapper.toCommentDto(comment);
    }

    @Override
    public List<CommentResponseDto> getComments(String id, int cPage, int cLimit) {

        List <CommentResponseDto> returnValue = new ArrayList<>();
        userRepository.findByUserId(id)
                .orElseThrow(()-> new UserNotFoundException("User not found"));
        if(cPage>0) cPage = cPage-1;

        Pageable pageableRequest = PageRequest.of(cPage, cLimit);
        Page<Comment> commentPage = commentRepository.findAll(pageableRequest);
        List<Comment> comments = commentPage.getContent();

        for (Comment comment : comments){
            Comment.builder()
                    .body(comment.getBody())
                    .post(comment.getPost())
                    .userId(comment.getUserId())
                    .username(comment.getUsername())
                    .build();

            CommentResponseDto commentResponseDto = Mapper.toCommentDto(comment);
            returnValue.add(commentResponseDto);

        }
        return returnValue;

    }

    @Override
    public CommentResponseDto updateComment(String id, Long postId, Long commentId, CommentRequestDto commentRequest) {

      User user =  userRepository.findByUserId(id)
                .orElseThrow(()-> new UserNotFoundException("User not found"));
      Post post =  postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post not found"));
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(()-> new ResourceNotFoundException("Comment not found"));
        if (!existingComment.getPost().getUser().equals(user) && !existingComment.getPost().equals(post))  {
            throw new UnauthorizedUserException("You are not authorized to update this comment");
        }
        existingComment.setBody(commentRequest.getBody());
        existingComment.setPost(post);
        existingComment.setUsername(commentRequest.getUsername());
        existingComment.setUserId(commentRequest.getUserId());
        Comment updatedComment = commentRepository.save(existingComment);
        return Mapper.toCommentDto(updatedComment);
    }

    @Override
    public void deleteComment(String id, Long postId, Long commentId) {
        User user = userRepository.findByUserId(id)
                .orElseThrow(()-> new UserNotFoundException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post resource not found"));
      Comment comment = commentRepository.findById(commentId)
              .orElseThrow(()-> new ResourceNotFoundException("Comment resource not found"));

        logger.info("User " + user);
        logger.info("Post " + post);
        if (comment.getPost().getUser().equals(user) && comment.getPost().equals(post))
            commentRepository.delete(comment);

    }
}
