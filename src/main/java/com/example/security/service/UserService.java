package com.example.security.service;

import com.example.security.domain.User;
import com.example.security.domain.dto.AdminDto;
import com.example.security.domain.dto.AuthSuccessDTO;
import com.example.security.domain.dto.ChangePasswordDto;
import com.example.security.domain.dto.ResultResponseDto;
import com.example.security.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Transactional
public class UserService {

  private final UserRepository userRepository;
  private final TokenService tokenService;
  private final PasswordEncoder passwordEncoder;
  private final JpaUserDetailsService jpaUserDetailsService;

    public static final String ADMIN_ROLE = "ROLE_ADMIN";
    public static final String USER_ROLE = "ROLE_USER";

  public UserService(UserRepository userRepository, TokenService tokenService, PasswordEncoder passwordEncoder, JpaUserDetailsService jpaUserDetailsService) {
    this.userRepository = userRepository;
    this.tokenService = tokenService;
    this.passwordEncoder = passwordEncoder;
    this.jpaUserDetailsService = jpaUserDetailsService;
  }

    public Optional<User> findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public AuthSuccessDTO login(Authentication authentication) {
        String token = tokenService.generateToken(authentication);
        User user = userRepository.findUserByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return new AuthSuccessDTO(token, user.toDto());
    }

    public ResultResponseDto create(AdminDto userJson) {
        if (userRepository.existsUserByUsername(userJson.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already taken");
        }
        if (!userJson.getPassword().equals(userJson.getConfirmationPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords don't match");
        }
        User user = new User(userJson.getUsername(), passwordEncoder.encode(userJson.getPassword()), ADMIN_ROLE);
        userRepository.save(user);
        return new ResultResponseDto("New user created successfully");
    }

    public ResultResponseDto changePassword(ChangePasswordDto userJson, String username) {
        User currentUser = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!userJson.getNewPassword().equals(userJson.getConfirmationNewPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords don't match");
        }
        currentUser.setPassword(passwordEncoder.encode(userJson.getNewPassword()));
        userRepository.save(currentUser);
        return new ResultResponseDto("Password changed");
    }

  public void createInitial(String initialUsername, String initialPassword) {
    if (!userRepository.existsUserByUsername(initialUsername)) {
      User initialAdmin = new User(initialUsername, passwordEncoder.encode(initialPassword), ADMIN_ROLE);
      userRepository.save(initialAdmin);
    }
  }
}