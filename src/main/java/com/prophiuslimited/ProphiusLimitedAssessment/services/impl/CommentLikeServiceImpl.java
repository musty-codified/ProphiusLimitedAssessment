package com.prophiuslimited.ProphiusLimitedAssessment.services.impl;

import com.prophiuslimited.ProphiusLimitedAssessment.dtos.responses.CommentLikeResponseDto;
import com.prophiuslimited.ProphiusLimitedAssessment.entities.Comment;
import com.prophiuslimited.ProphiusLimitedAssessment.entities.CommentLike;
import com.prophiuslimited.ProphiusLimitedAssessment.exceptions.ResourceNotFoundException;
import com.prophiuslimited.ProphiusLimitedAssessment.exceptions.UserNotFoundException;
import com.prophiuslimited.ProphiusLimitedAssessment.repositories.CommentLikeRepository;
import com.prophiuslimited.ProphiusLimitedAssessment.repositories.CommentRepository;
import com.prophiuslimited.ProphiusLimitedAssessment.repositories.PostRepository;
import com.prophiuslimited.ProphiusLimitedAssessment.repositories.UserRepository;
import com.prophiuslimited.ProphiusLimitedAssessment.services.CommentLikeService;
import com.prophiuslimited.ProphiusLimitedAssessment.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CommentLikeServiceImpl implements CommentLikeService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    private final AppUtils appUtil;
    private final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Override
    public CommentLikeResponseDto updateCommentLike(String userId, Long postId, Long commentId) {

        userRepository.findByUserId(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found"));
         postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post not found"));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new ResourceNotFoundException("Comment not found"));

//        CommentLike commentLike = commentLikeRepository.findAllByCommentIdAndPostIdAndUserId(commentId, postId, userId);
        CommentLike commentLike = commentLikeRepository.findByCommentIdAndUserId(commentId, userId);
        appUtil.print("Comment like: " + commentLike);

        if (commentLike == null) {
            // If the commentLike doesn't exist, create a new one and set its properties
            commentLike = new CommentLike();
            commentLike.setLiked(true);
            commentLike.setUserId(userId);
            commentLike.setPostId(postId);
            commentLike.setComment(comment);
            commentLike.getComment().setLikesCount(comment.getLikesCount() + 1);

            commentLikeRepository.save(commentLike);


            //Increment the likesCount of the Associated Comment entity
//            comment.setLikesCount(comment.getLikesCount() + 1);
        } else {
            // If the commentLike exists, toggle its liked status
            boolean liked = commentLike.isLiked();
            commentLike.setLiked(!liked);
            commentLikeRepository.save(commentLike);

            if (liked){
                comment.setLikesCount(comment.getLikesCount() - 1);
            } else {
                comment.setLikesCount(comment.getLikesCount() + 1);
            }

        }

        commentRepository.save(comment);
        CommentLikeResponseDto commentLikeResponseDto = appUtil.getMapper().convertValue(commentLike, CommentLikeResponseDto.class);

        return commentLikeResponseDto;
    }
}
