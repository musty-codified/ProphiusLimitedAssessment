package com.prophiuslimited.ProphiusLimitedAssessment.services;


public interface FollowService {
    void followUser(String followerId, String followeeId);
    void unfollowUser(String followerId, String followeeId);
}
