package com.prophiuslimited.ProphiusLimitedAssessment.services.impl;

import com.prophiuslimited.ProphiusLimitedAssessment.entities.User;
import com.prophiuslimited.ProphiusLimitedAssessment.exceptions.UserNotFoundException;
import com.prophiuslimited.ProphiusLimitedAssessment.repositories.UserRepository;
import com.prophiuslimited.ProphiusLimitedAssessment.services.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
    private final UserRepository userRepository;
    @Override
    public void followUser(String followerId, String followeeId) {
      User follower = userRepository.findByUserId(followerId)
                .orElseThrow(()-> new UserNotFoundException("User not found with Id: " + followerId));
      User followee = userRepository.findByUserId(followeeId)
                .orElseThrow(()-> new UserNotFoundException("User not found with Id: " + followeeId));
      follower.getFollowing().add(followee);
      userRepository.save(follower);
    }

    @Override
    public void unfollowUser(String followerId, String followeeId) {
        User follower = userRepository.findByUserId(followerId)
                .orElseThrow(()-> new UserNotFoundException("User not found with Id: " + followerId));
        User followee = userRepository.findByUserId(followeeId)
                .orElseThrow(()-> new UserNotFoundException("User not found with Id: " + followeeId));
        follower.getFollowing().remove(followee);
        userRepository.save(follower);
    }
}
