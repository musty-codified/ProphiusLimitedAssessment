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
        User follower = follows(followeeId);
       User followee = follows(followeeId);
      follower.getFollowing().add(followee);
      userRepository.save(follower);
    }

    private User follows(String userId){
        return userRepository.findByUserId(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found with Id: " + userId));
    }

    @Override
    public void unfollowUser(String followerId, String followeeId) {
        User follower = follows(followeeId);
        User followee = follows(followeeId);
        follower.getFollowing().remove(followee);
        userRepository.save(follower);
    }
}
