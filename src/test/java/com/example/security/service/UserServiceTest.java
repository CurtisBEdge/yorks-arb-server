package com.example.security.service;

import com.example.security.domain.User;
import com.example.security.domain.dto.AdminDto;
import com.example.security.domain.dto.ChangePasswordDto;
import com.example.security.domain.dto.ResultResponseDto;
import com.example.security.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.example.security.domain.dto.AuthSuccessDTO;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


class UserServiceTest {

    private final String initialUsername = "LittleSteve";
    private final String initialPassword = "password";
    private final String encodedPassword = "password1";

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private PasswordEncoder passwordEncoderMock;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private UserService userServiceTest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void createInitialUserAlreadyExists() {
        when(userRepositoryMock.existsUserByUsername(initialUsername)).thenReturn(true);
        userServiceTest.createInitial(initialUsername, initialPassword);
        verify(userRepositoryMock, never()).save(any(User.class));
    }

    @Test
    void createInitialUserDoesNotAlreadyExist() {

        when(userRepositoryMock.existsUserByUsername(initialUsername)).thenReturn(false);
        when(passwordEncoderMock.encode(initialPassword)).thenReturn(encodedPassword);
        userServiceTest.createInitial(initialUsername, initialPassword);
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepositoryMock).save(argumentCaptor.capture());
        User initialAdmin = argumentCaptor.getValue();
        assertEquals(initialUsername, initialAdmin.getUsername());
        assertEquals(encodedPassword, initialAdmin.getPassword());
    }

    @Test
    void loginUserExists() {
        Authentication authentication = mock(Authentication.class);
        String token = "token";
        when(tokenService.generateToken(authentication)).thenReturn(token);
        User fakeUser = new User("fake", "fakePassword", "ADMIN_ROLE");
        when(userRepositoryMock.findUserByUsername(authentication.getName())).thenReturn(Optional.of(fakeUser));
        AuthSuccessDTO testResult = userServiceTest.login(authentication);
        AuthSuccessDTO createdDto = new AuthSuccessDTO(token, fakeUser.toDto());
        assertEquals(testResult.getToken(), createdDto.getToken());
        assertEquals(testResult.getUser().getUsername(), createdDto.getUser().getUsername());
    }

    @Test
    void loginUserDoesNotExist() {
        Authentication authentication = mock(Authentication.class);
        when(userRepositoryMock.findUserByUsername(authentication.getName())).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> userServiceTest.login(authentication));
    }

    @Test
    void usernameExists() {
        AdminDto adminDto = new AdminDto();
        adminDto.setUsername("alreadyExists");
        adminDto.setPassword("Password1");
        when(userRepositoryMock.existsUserByUsername(adminDto.getUsername())).thenReturn(true);
        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            userServiceTest.create(adminDto);
        });
        assertEquals(thrown.getReason(), "Username already taken");
    }

    @Test
    void passwordDontMatch() {
        AdminDto adminDto = new AdminDto();
        adminDto.setUsername("user");
        adminDto.setPassword("Password1");
        adminDto.setConfirmationPassword("Password2");
        when(userRepositoryMock.existsUserByUsername(adminDto.getUsername())).thenReturn(false);
        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            userServiceTest.create(adminDto);
        });
        assertEquals(thrown.getReason(), "Passwords don't match");
    }

    @Test
    void newAdminIsCreated() {
        AdminDto adminDto = new AdminDto();
        adminDto.setUsername("user");
        adminDto.setPassword("Password1");
        adminDto.setConfirmationPassword("Password1");
        when(userRepositoryMock.existsUserByUsername(adminDto.getUsername())).thenReturn(false);
        when(passwordEncoderMock.encode("Password1")).thenReturn("Password1");
        ResultResponseDto actual = userServiceTest.create(adminDto);
        verify(userRepositoryMock, times(1)).save(any());
        assertEquals("New user created successfully", actual.getResult());
    }

    @Test
    void userNotFound() {
        String username = "username";
        ChangePasswordDto changePasswordDto = new ChangePasswordDto();
        when(userRepositoryMock.findUserByUsername(username)).thenReturn(Optional.empty());
        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            userServiceTest.changePassword(changePasswordDto, username);
        });
        assertEquals(thrown.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void changePasswordDontMatch() {
        User currentUser = new User("username", "Password1", "ROLE_ADMIN");
        ChangePasswordDto changePasswordDto = new ChangePasswordDto();
        changePasswordDto.setNewPassword("Password2");
        changePasswordDto.setConfirmationNewPassword("Password1");
        when(userRepositoryMock.findUserByUsername(currentUser.getUsername())).thenReturn(Optional.of(currentUser));
        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            userServiceTest.changePassword(changePasswordDto, "username");
        });
        assertEquals(thrown.getReason(), "Passwords don't match");
    }

    @Test
    void changePasswordSuccess() {
        User currentUser = new User("username", "Password1", "ROLE_ADMIN");
        ChangePasswordDto changePasswordDto = new ChangePasswordDto();
        changePasswordDto.setNewPassword("Password2");
        changePasswordDto.setConfirmationNewPassword("Password2");
        when(userRepositoryMock.findUserByUsername(currentUser.getUsername())).thenReturn(Optional.of(currentUser));
        when(passwordEncoderMock.encode("Password2")).thenReturn("Password2");
        ResultResponseDto actual = userServiceTest.changePassword(changePasswordDto, "username");
        verify(userRepositoryMock, times(1)).save(any());
        assertEquals("Password changed", actual.getResult());
    }

}
