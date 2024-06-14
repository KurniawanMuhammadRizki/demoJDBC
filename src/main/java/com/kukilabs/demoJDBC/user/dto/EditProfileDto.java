package com.kukilabs.demoJDBC.user.dto;

import com.kukilabs.demoJDBC.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EditProfileDto {
    @NotBlank(message = "Name is Required")
    private String name;
    @NotBlank(message = "Email is Required")
    private String email;
    @NotBlank(message = "Password is Required")
    private String profilePhotoUrl;
    @NotBlank(message = "Password is Required")
    private String quotes;
    @NotNull
    private int languageId;

    public User toEntity(){
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setProfileImgUrl(profilePhotoUrl);
        user.setQuotes(quotes);
        user.setLanguageId(languageId);
        return user;
    }
}
