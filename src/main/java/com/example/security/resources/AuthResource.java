package com.example.security.resources;

import com.example.security.domain.dto.AdminDto;
import com.example.security.domain.dto.AuthSuccessDTO;
import com.example.security.domain.dto.ChangePasswordDto;
import com.example.security.domain.dto.ResultResponseDto;
import com.example.security.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthResource {

    private final UserService userService;

    public AuthResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthSuccessDTO> login(Authentication authentication) {
        AuthSuccessDTO authSuccessDTO = userService.login(authentication);
        return new ResponseEntity<>(authSuccessDTO, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<ResultResponseDto> create(@Valid @RequestBody AdminDto adminDto){
        ResultResponseDto resultResponseDto = userService.create(adminDto);
        return new ResponseEntity<>(resultResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/editPassword")
    public ResponseEntity<ResultResponseDto> changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto, JwtAuthenticationToken principal){
        ResultResponseDto resultResponseDto = userService.changePassword(changePasswordDto, principal.getName());
        return new ResponseEntity<>(resultResponseDto, HttpStatus.OK);
    }
}
