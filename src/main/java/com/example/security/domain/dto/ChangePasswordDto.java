package com.example.security.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ChangePasswordDto {
    @NotEmpty(message = "Password required")
    @Size(min = 8, message = "Must be at least 8 characters")
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*", message = "Password must contain at least 1 uppercase and at least 1 number")
    private String newPassword;
    private String confirmationNewPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmationNewPassword() {
        return confirmationNewPassword;
    }

    public void setConfirmationNewPassword(String confirmationNewPassword) {
        this.confirmationNewPassword = confirmationNewPassword;
    }
}
