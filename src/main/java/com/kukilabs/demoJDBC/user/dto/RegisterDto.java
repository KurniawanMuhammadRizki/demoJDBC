package com.kukilabs.demoJDBC.user.dto;

import com.kukilabs.demoJDBC.user.entity.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterDto {
    @NotBlank(message = "Name is Required")
    private String name;
    @NotBlank(message = "Email is Required")
    private String email;
    @NotBlank(message = "Password is Required")
    private String password;
    @NotNull
    private int languageId;

    public User toEntity(){
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setLanguageId(languageId);
        return user;
    }
}
