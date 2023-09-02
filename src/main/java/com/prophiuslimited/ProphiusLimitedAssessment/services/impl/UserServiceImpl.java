package com.prophiuslimited.ProphiusLimitedAssessment.services.impl;

import com.prophiuslimited.ProphiusLimitedAssessment.dtos.requests.LoginRequestDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.requests.SignupRequestDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.requests.UpdateUserRequestDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.responses.LoginResponseDto;
import com.prophiuslimited.ProphiusLimitedAssessment.dtos.responses.UserResponseDto;
import com.prophiuslimited.ProphiusLimitedAssessment.entities.User;
import com.prophiuslimited.ProphiusLimitedAssessment.exceptions.RecordAlreadyExistException;
import com.prophiuslimited.ProphiusLimitedAssessment.exceptions.UserNotFoundException;
import com.prophiuslimited.ProphiusLimitedAssessment.repositories.UserRepository;
import com.prophiuslimited.ProphiusLimitedAssessment.security.CustomUserDetailService;
import com.prophiuslimited.ProphiusLimitedAssessment.security.JwtUtils;
import com.prophiuslimited.ProphiusLimitedAssessment.services.UserService;
import com.prophiuslimited.ProphiusLimitedAssessment.utils.AppUtils;
import com.prophiuslimited.ProphiusLimitedAssessment.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AppUtils appUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailService customUserDetailService;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final JwtUtils jwtUtil;

    @Override
    public UserResponseDto signUp(SignupRequestDto signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail()))
            throw new RecordAlreadyExistException("Record already exist");
            User user = User.builder()
                .userId(appUtil.generateUserId(10))
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .username(signupRequest.getUsername())
                .posts(new HashSet<>())
                .profilePicture("http://img")
                .build();

         User storedUser = userRepository.save(user);
            return Mapper.toUserDto(storedUser);
    }

    @Override
    public UserResponseDto getUser(String userId) {
      User user =  userRepository.findByUserId(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found with ID " + userId));
        return Mapper.toUserDto(user);
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("User not found with email " + email));
        return Mapper.toUserDto(user);
    }

    @Override
    public Page<UserResponseDto> getUsers(int page, int limit, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        Pageable pageableRequest = PageRequest.of(page, limit, sort);
        Page<User> userPage = userRepository.findAll(pageableRequest);

        List<UserResponseDto> userResponseDtos = userPage.stream()
                .map(Mapper::toUserDto).collect(Collectors.toList());
        if (page > 0) page = page -1;
        int max = Math.min(limit * (page + 1), userResponseDtos.size());
        int min = page * limit;
        return new PageImpl<>(userResponseDtos.subList(min, max), pageableRequest, userResponseDtos.size());

    }

    @Override
    public UserResponseDto updateUser(String userId, UpdateUserRequestDto updateRequestDto) {
        User user = userRepository.findByUserId(userId).
                orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setEmail(updateRequestDto.getEmail());
        user.setUsername(updateRequestDto.getUsername());
        user.setUserId(appUtil.generateUserId(10));
        User updatedUser = userRepository.save(user);

        return Mapper.toUserDto(updatedUser);

    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found"));
        userRepository.delete(user);
    }

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(()
                -> new BadCredentialsException("Bad credentials"));
        logger.info("Email is: " + request.getEmail());

        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (Exception e) {
            throw new BadCredentialsException("Bad credentials");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = customUserDetailService.loadUserByUsername(request.getEmail());

        String token = jwtUtil.generateToken(userDetails.getUsername(), user.getUserId());

         LoginResponseDto responseDto = LoginResponseDto.builder()
                 .username(user.getUsername())
                 .email(user.getEmail())
                 .token(token)
                 .build();
        return responseDto;
    }
}
