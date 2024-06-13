package com.kukilabs.demoJDBC.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordDto {
    @NotBlank(message = "Current password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String currentPassword;

    @NotBlank(message = "New password is required")
    @Size(min = 8, message = "New password must be at least 8 characters long")
    private String newPassword;
}
