package com.prophiuslimited.ProphiusLimitedAssessment.services.impl;

import com.prophiuslimited.ProphiusLimitedAssessment.dtos.*;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AppUtils appUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailService customUserDetailService;

    private final JwtUtils jwtUtil;

    @Override
    public UserResponseDto signUp(SignupRequestDto signupRequest) {
        boolean emailExist = userRepository.existsByEmail(signupRequest.getEmail());

        if (emailExist)
            throw new RecordAlreadyExistException("Record already exist in database");
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
                .orElseThrow(()-> new UserNotFoundException("User with ID " + userId + " not found"));
        return Mapper.toUserDto(user);
    }

    @Override
    public List<UserResponseDto> getUsers(int page, int limit, String sortBy, String sortDir) {
        List<UserResponseDto> returnValue = new ArrayList<>();

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        if(page>0) page = page-1;
        Pageable pageableRequest = PageRequest.of(page, limit, sort);
        Page<User> userPage = userRepository.findAll(pageableRequest);
        List<User> users = userPage.getContent();

        for (User user : users){
            UserResponseDto userResponseDto = Mapper.toUserDto(user);
            returnValue.add(userResponseDto);

        }

        return returnValue;
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

        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (Exception e) {
            throw new BadCredentialsException("Bad credentials");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = customUserDetailService.loadUserByUsername(request.getEmail());
        String token = jwtUtil.generateToken(userDetails.getUsername());

         LoginResponseDto responseDto = LoginResponseDto.builder()
                 .username(user.getUsername())
                 .email(user.getEmail())
                 .token(token)
                 .build();
        return responseDto;
    }
}
