package com.prophiuslimited.ProphiusLimitedAssessment.controllers;
import com.prophiuslimited.ProphiusLimitedAssessment.exceptions.ValidationException;
import com.prophiuslimited.ProphiusLimitedAssessment.services.FollowService;
import com.prophiuslimited.ProphiusLimitedAssessment.utils.ResponseManager;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class FollowController {

    private final FollowService followService;
    @Operation(summary = "Endpoint to follow a user. ",
    description = "Add a specific user to followers list"
    )

    @PostMapping("/{followerId}/follow/{followeeId}")
    public ResponseEntity<?> followUser(@PathVariable String followerId, @PathVariable String followeeId) {

        // Get the current HTTP request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();

        // Get the authenticated userId from the request attribute
        String authenticatedUserId = (String) request.getAttribute("userId");

        // Compare the authenticated userId with the userId from the path variable
        if (!authenticatedUserId.equals(followerId)) {
            throw new ValidationException("Invalid userId.");
        }
        followService.followUser(followerId, followeeId);
        return new ResponseEntity<>(HttpStatus.OK) ;
    }

    @Operation(summary = "Endpoint to Unfollow a user. ",
     description = "Remove a specific user from the followers list"
    )
    @PostMapping("/{followerId}/unfollow/{followeeId}")
    public ResponseEntity<HttpStatus> unfollowUser(@PathVariable String followerId, @PathVariable String followeeId) {
        // Get the current HTTP request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();

        // Get the authenticated userId from the request attribute
        String authenticatedUserId = (String) request.getAttribute("userId");

        // Compare the authenticated userId with the userId from the path variable
        if (!authenticatedUserId.equals(followerId)) {
            throw new ValidationException("Invalid userId.");
        }
        followService.unfollowUser(followerId, followeeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
