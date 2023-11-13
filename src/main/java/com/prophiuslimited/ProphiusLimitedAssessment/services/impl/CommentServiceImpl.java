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
import com.prophiuslimited.ProphiusLimitedAssessment.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final AppUtils appUtil;
    private final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Override
    public CommentResponseDto createComment(String userId, Long postId, CommentRequestDto commentRequest) {
        userRepository.findByUserId(userId).orElseThrow(()-> new UserNotFoundException("User not found with ID: " + userId));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + postId));

        Set<CommentLike> commentLikes = new HashSet<>();
        Comment newComment = appUtil.getMapper().convertValue(commentRequest, Comment.class);
        newComment.setPost(post);

        CommentLike commentLike = new CommentLike();
        commentLike.setLiked(false); // Set the default liked status if needed
        commentLike.setComment(newComment);// Associate the CommentLike with the new Comment
        commentLike.setUserId(userId);
        commentLike.setPostId(postId);
        commentLikes.add(commentLike);

        newComment.setCommentLikes(commentLikes);

        post.getComments().add(newComment);

        Comment savedComment = commentRepository.save(newComment);

      CommentResponseDto commentResponseDto =
              appUtil.getMapper().convertValue(savedComment, CommentResponseDto.class);
      commentResponseDto.setPostId(postId);
        return commentResponseDto;
    }

    @Override
    public CommentResponseDto getComment(String userId, Long postId, Long commentId) {
        logger.info("UserId: " + userId + " , postId: " + postId + " , commentId: " + commentId );
        userRepository.findByUserId(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found"));
        postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post not found"));
      Comment comment = commentRepository.findByPostIdAndId(postId, commentId)
                      .orElseThrow(()-> new ResourceNotFoundException("Comment not found"));
      CommentResponseDto commentResponseDto = appUtil.getMapper().convertValue(comment, CommentResponseDto.class);
      commentResponseDto.setPostId(postId);
        return commentResponseDto;
    }

    @Override
    public Page<CommentResponseDto> getComments(String userId, Long postId, int cPage, int cLimit, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();
       userRepository.findByUserId(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found"));
        postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post not found"));

        Pageable pageRequest = PageRequest.of(cPage, cLimit, sort);
        Page <Comment> commentPages = commentRepository.findAllByUserIdAndPostId(userId, postId, pageRequest);
        List <CommentResponseDto> commentResponseDtos =
                commentPages.stream().map(comment -> appUtil.getMapper().convertValue(comment, CommentResponseDto.class))
                        .collect(Collectors.toList());
        commentResponseDtos.forEach(commentResponseDto ->
            commentResponseDto.setPostId(postId));

             if(cPage>0) cPage = cPage-1;
          int max = Math.min(cLimit * (cPage + 1), commentResponseDtos.size());
          int min = cPage * cLimit;

        return new PageImpl<>(commentResponseDtos.subList(min, max), pageRequest, commentResponseDtos.size());

    }

    @Override
    public CommentResponseDto updateComment(String userId, Long postId, Long commentId, CommentRequestDto commentRequest) {

      User user = userRepository.findByUserId(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found"));
      Post post = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post not found"));
     Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(()-> new ResourceNotFoundException("Comment not found"));

        existingComment.setBody(commentRequest.getBody());
        existingComment.setPost(post);
       Comment updatedComment = commentRepository.save(existingComment);

       CommentResponseDto commentResponseDto =
               appUtil.getMapper().convertValue(updatedComment, CommentResponseDto.class);
       commentResponseDto.setPostId(commentRequest.getPostId());
       commentResponseDto.setUserId(commentRequest.getUserId());
        return commentResponseDto;
    }

    @Override
    public void deleteComment(String userId, Long postId, Long commentId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post not found"));
      Comment comment = commentRepository.findById(commentId)
              .orElseThrow(()-> new ResourceNotFoundException("Comment not found"));
        if (!comment.getUserId().equals(user.getUserId())) {
            throw new UnauthorizedUserException("Forbidden");
        }
            commentRepository.delete(comment);


    }
}
