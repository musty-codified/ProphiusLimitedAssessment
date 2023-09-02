package com.prophiuslimited.ProphiusLimitedAssessment.security;

import com.prophiuslimited.ProphiusLimitedAssessment.entities.User;
import com.prophiuslimited.ProphiusLimitedAssessment.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        String password = user.getPassword() == null || user.getPassword().isEmpty() ? "****" : user.getPassword();
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), password, Arrays.asList());
    }

    @Transactional
    public UserDetails loadUserById(String userId){
        User user = userRepository.findByUserId(userId).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + userId)
        );

        return new org.springframework.security.core.userdetails.User(user.getUserId(),
                user.getPassword(), new ArrayList<>());
    }
}