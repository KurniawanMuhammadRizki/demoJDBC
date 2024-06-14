package com.kukilabs.demoJDBC.user.service;

import com.kukilabs.demoJDBC.user.dto.*;
import com.kukilabs.demoJDBC.user.entity.User;

public interface UserService {
    void createUser(User user);

    User register(RegisterDto user);
    User getUserByEmail(String email);

    void changePassword(PasswordDto passwordDto, Long userId);
    void setUpPin(PinDto pin, Long userId);
    void editProfile(EditProfileDto editProfileDto, Long userId);

    void forgetPassword(ForgetPasswordDto forgetPasswordDto);
}
