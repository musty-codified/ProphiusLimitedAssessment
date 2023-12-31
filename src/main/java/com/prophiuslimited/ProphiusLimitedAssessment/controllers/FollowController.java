package com.prophiuslimited.ProphiusLimitedAssessment.controllers;
import com.prophiuslimited.ProphiusLimitedAssessment.services.FollowService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class FollowController {
    private final FollowService followService;

    @Operation(summary = "Endpoint to follow a user. ", description = "Add a specific user to followers list")
    @PostMapping("/{followerId}/follow/{followeeId}")
    public ResponseEntity<HttpStatus> followUser(@PathVariable String followerId, @PathVariable String followeeId) {

        followService.followUser(followerId, followeeId);
        return new ResponseEntity<>(HttpStatus.OK) ;
    }

    @Operation(summary = "Endpoint to Unfollow a user. ", description = "Remove a specific user from followers list")
    @PostMapping("/{followerId}/unfollow/{followeeId}")
    public ResponseEntity<HttpStatus> unfollowUser(@PathVariable String followerId, @PathVariable String followeeId) {

        followService.unfollowUser(followerId, followeeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
