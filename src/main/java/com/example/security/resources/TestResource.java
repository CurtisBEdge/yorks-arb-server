package com.example.security.resources;

import com.example.security.domain.Sign;
import com.example.security.domain.User;
import com.example.security.domain.dto.ResultResponseDto;
import com.example.security.domain.dto.SignDto;
import com.example.security.repository.SignRepository;
import com.example.security.repository.UserRepository;
import com.example.security.service.UserService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@Profile("test")
public class TestResource {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SignRepository signRepository;

    public TestResource(UserRepository userRepository, PasswordEncoder passwordEncoder, SignRepository signRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.signRepository = signRepository;
    }

    @DeleteMapping("/users")
    public ResponseEntity<ResultResponseDto> resetUsers() {
        userRepository.deleteAll();
        return new ResponseEntity<>(new ResultResponseDto("Done"), HttpStatus.OK);
    }

    @PostMapping("/create-admin")
    public ResponseEntity<ResultResponseDto> createTestAdmin() {
        userRepository.save(new User("admin", passwordEncoder.encode("Password1"), UserService.ADMIN_ROLE));
        return new ResponseEntity<>(new ResultResponseDto("Done"), HttpStatus.OK);
    }

    @DeleteMapping("/signs")
    public ResponseEntity<ResultResponseDto> resetSigns() {
        signRepository.deleteAll();
        return new ResponseEntity<>(new ResultResponseDto("Done"), HttpStatus.OK);
    }

    @PostMapping("/create-sign")
    public ResponseEntity<SignDto> createTestSign() {
        SignDto signDto =
                signRepository.save(new Sign( "Placeholder Title", "Placeholder description", 23.6, 67.5)).dto();
        return new ResponseEntity<>(signDto, HttpStatus.OK);
    }

}


