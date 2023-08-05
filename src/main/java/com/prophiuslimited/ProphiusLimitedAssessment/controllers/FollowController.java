package com.prophiuslimited.ProphiusLimitedAssessment.controllers;


import com.prophiuslimited.ProphiusLimitedAssessment.repositories.UserRepository;
import com.prophiuslimited.ProphiusLimitedAssessment.services.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{followerId}/follow/{followeeId}")
    public ResponseEntity<?> followUser(@PathVariable String followerId, @PathVariable String followeeId) {
        followService.followUser(followerId, followeeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{followerId}/unfollow/{followeeId}")
    public ResponseEntity<HttpStatus> unfollowUser(@PathVariable String followerId, @PathVariable String followeeId) {
        followService.unfollowUser(followerId, followeeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
