package com.prophiuslimited.ProphiusLimitedAssessment.utils;


import com.prophiuslimited.ProphiusLimitedAssessment.dtos.*;
import com.prophiuslimited.ProphiusLimitedAssessment.entities.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Mapper {

    private static final Logger logger = LoggerFactory.getLogger(Mapper.class);
    public static UserResponseDto toUserDto(User user) {
        return UserResponseDto.builder()
                .userId(user.getUserId())
                .userName(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public static PostResponseDto toPostDto(Post post) {

        List<PostLikeResponseDto> postLikeResponses = new ArrayList<>();
        for (PostLike postLike : post.getPostLikes()) {
            PostLikeResponseDto postLikeResponseDto = toPostLikeDto(postLike);
            postLikeResponses.add(postLikeResponseDto);
        }

        List<CommentResponseDto> commentResponses = new ArrayList<>();
        if (post.getComments() != null) {
        for (Comment comment : post.getComments()) {
            logger.info("comments: " + post.getComments());
            CommentResponseDto commentResponseDto = toCommentDto(comment);
            commentResponses.add(commentResponseDto);
           }
            post.setComments(new HashSet<>());

        }

        return PostResponseDto.builder()
                .content(post.getContent())
                .username(post.getUser().getUsername())
                .userId(post.getUser().getUserId())
                .commentResponses(commentResponses)
                .postLikeResponses(postLikeResponses)
                .build();
    }

    public static CommentResponseDto toCommentDto(Comment comment) {

        List<CommentLikeResponseDto> commentLikeResponses = new ArrayList<>();
        for (CommentLike commentLike : comment.getCommentLikes()) {
            CommentLikeResponseDto commentLikeResponseDto = toCommentLikeDto(commentLike);
            commentLikeResponses.add(commentLikeResponseDto);
        }

        return CommentResponseDto.builder()
                .body(comment.getBody())
                .postId(comment.getPost().getId())
                .userId(comment.getUserId())
                .username(comment.getUsername())
                .commentLikeResponses(commentLikeResponses)
                .build();
    }


    public static PostLikeResponseDto toPostLikeDto(PostLike postLike) {

        return PostLikeResponseDto.builder()
                .liked(postLike.isLiked())
                .postId(postLike.getPost().getId())
                .likesCount(postLike.getPost().getLikesCount())
                .userId(postLike.getUserId())
                .id(postLike.getId())
                .build();
    }

    public static CommentLikeResponseDto toCommentLikeDto(CommentLike commentLike) {

        return CommentLikeResponseDto.builder()
                .liked(commentLike.isLiked())
                .likesCount(commentLike.getComment().getLikesCount())
                .postId(commentLike.getPostId())
                .userId(commentLike.getUserId())
                .id(commentLike.getId())
                .build();
    }
}
