package com.example.security.domain.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Set;

import static com.example.security.domain.dto.AdminDto.passwordRegexError;
import static org.junit.jupiter.api.Assertions.*;

class AdminDtoTest {
    Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void passwordHasNoNumbers() {
        AdminDto adminDto = new AdminDto();
        adminDto.setUsername("user");
        adminDto.setPassword("Password");
        Set<ConstraintViolation<AdminDto>> violationSet = validator.validate(adminDto);
        assertTrue(violationSet.stream().anyMatch(adminDtoConstraintViolation -> adminDtoConstraintViolation.getMessage().equals(passwordRegexError)));
    }

    @Test
    void passwordHasNoUpperCase() {
        AdminDto adminDto = new AdminDto();
        adminDto.setPassword("password1");
        Set<ConstraintViolation<AdminDto>> violationSet = validator.validate(adminDto);
        assertTrue(violationSet.stream().anyMatch(adminDtoConstraintViolation -> adminDtoConstraintViolation.getMessage().equals(passwordRegexError)));
    }

    @Test
    void passwordHasNoLowerCase() {
        AdminDto adminDto = new AdminDto();
        adminDto.setPassword("PASSWORD1");
        Set<ConstraintViolation<AdminDto>> violationSet = validator.validate(adminDto);
        assertTrue(violationSet.stream().anyMatch(adminDtoConstraintViolation -> adminDtoConstraintViolation.getMessage().equals(passwordRegexError)));
    }

    @Test
    void passwordIsValid() {
        AdminDto adminDto = new AdminDto();
        adminDto.setUsername("user");
        adminDto.setPassword("Password1");
        Set<ConstraintViolation<AdminDto>> violationSet = validator.validate(adminDto);
        assertTrue(violationSet.isEmpty());
    }

}