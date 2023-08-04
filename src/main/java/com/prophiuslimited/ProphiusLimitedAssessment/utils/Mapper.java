package com.prophiuslimited.ProphiusLimitedAssessment.utils;


import com.prophiuslimited.ProphiusLimitedAssessment.dtos.CommentResponseDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.PostResponseDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.UserResponseDto;
import com.prophiuslimited.ProphiusLimitedAssessment.entities.Comment;
import com.prophiuslimited.ProphiusLimitedAssessment.entities.Post;
import com.prophiuslimited.ProphiusLimitedAssessment.entities.User;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public static UserResponseDto toUserDto(User user) {
        return UserResponseDto.builder()
                .userId(user.getUserId())
                .userName(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public static PostResponseDto toPostDto(Post post) {
        return PostResponseDto.builder()
                .content(post.getContent())
                .likesCount(post.getLikesCount())
                .username(post.getUser().getUsername())
                .userId(post.getUser().getUserId())
                .build();
    }

    public static CommentResponseDto toCommentDto(Comment comment) {
        return CommentResponseDto.builder()
                .body(comment.getBody())
                .postId(comment.getPost().getId())
                .userId(comment.getUserId())
                .username(comment.getUsername())
                .build();
    }
}
