package com.prophiuslimited.ProphiusLimitedAssessment.services.impl;

import com.prophiuslimited.ProphiusLimitedAssessment.dtos.requests.CommentRequestDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.responses.CommentResponseDto;
import com.prophiuslimited.ProphiusLimitedAssessment.entities.*;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Override
    public CommentResponseDto createComment(String userId, Long postId, CommentRequestDto commentRequest) {
        userRepository.findByUserId(userId).orElseThrow(()-> new UserNotFoundException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        Set<CommentLike> commentLikes = new HashSet<>();

        Comment comment = Comment.builder()
               .body(commentRequest.getBody())
               .userId(commentRequest.getUserId())
               .username(commentRequest.getUsername())
               .post(post)
               .build();

        CommentLike commentLike = new CommentLike();
        commentLike.setLiked(false); // Set the default liked status if needed
        commentLike.setComment(comment);// Associate the CommentLike with the new Commentt
        commentLike.setUserId(userId);
        commentLikes.add(commentLike);

        comment.setCommentLikes(commentLikes);

        post.getComments().add(comment);

        Comment savedComment = commentRepository.save(comment);

        return Mapper.toCommentDto(savedComment);
    }

    @Override
    public CommentResponseDto getComment(String userId, Long postId, Long commentId) {
        logger.info("UserId: " + userId + " , postId: " + postId + " , commentId: " + commentId );
        userRepository.findByUserId(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found"));
        postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post resource not found"));
      Comment comment = commentRepository.findByPostIdAndId(postId, commentId)
                      .orElseThrow(()-> new ResourceNotFoundException("Comment Resource not found"));

        return Mapper.toCommentDto(comment);
    }

    @Override
    public List<CommentResponseDto> getComments(String userId, Long postId, int cPage, int cLimit, String sortBy, String sortDir) {

        List <CommentResponseDto> returnValue = new ArrayList<>();

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();
       userRepository.findByUserId(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        postRepository.findById(postId)
                .orElseThrow(()-> new UserNotFoundException("Post not found"));

        if(cPage>0) cPage = cPage-1;

        Pageable pageableRequest = PageRequest.of(cPage, cLimit, sort);
        Page<Comment> commentPage = commentRepository.findAllByUserIdAndPostId(userId, postId, pageableRequest);

        List<Comment> comments = commentPage.getContent();

        for (Comment comment : comments){
            CommentResponseDto commentResponseDto = Mapper.toCommentDto(comment);
            returnValue.add(commentResponseDto);

        }
        return returnValue;

    }

    @Override
    public CommentResponseDto updateComment(String userId, Long postId, Long commentId, CommentRequestDto commentRequest) {

      User user =  userRepository.findByUserId(userId)
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
    public void deleteComment(String userId, Long postId, Long commentId) {
        User user = userRepository.findByUserId(userId)
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
