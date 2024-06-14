package com.kukilabs.demoJDBC.user.controller;

import com.kukilabs.demoJDBC.responses.Response;
import com.kukilabs.demoJDBC.user.dto.*;
import com.kukilabs.demoJDBC.user.entity.User;
import com.kukilabs.demoJDBC.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }
    public Long getAuthorizedUserId(){
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        String requesterEmail = auth.getName();
        User user = userService.getUserByEmail(requesterEmail);
        return user.getId();
    }
    @PostMapping
    public void createUser(@RequestBody User user){
        userService.createUser(user);
    }

    @PostMapping("/register")
    public ResponseEntity<Response<User>> register(@RequestBody RegisterDto registerDto) {
        var userRegistered = userService.register(registerDto);
        return Response.successfulResponse("User registered successfully");
    }

    @PostMapping("/setup-pin")
    public ResponseEntity<Response<User>> setupPin(@RequestBody PinDto pin) {
        Long userId = getAuthorizedUserId();
        userService.setUpPin(pin, userId);
        return Response.successfulResponse("password change successfully");
    }

    @PostMapping("/edit-profile")
    public ResponseEntity<Response<User>> editProfile(@RequestBody EditProfileDto editProfileDto) {
        Long userId = getAuthorizedUserId();
        userService.editProfile(editProfileDto, userId);
        return Response.successfulResponse("Profile updated successfully");
    }

    @PostMapping("/forget-password")
    public ResponseEntity<Response<User>> forgetPassword(@RequestBody ForgetPasswordDto forgetPasswordDto) {
        userService.forgetPassword(forgetPasswordDto);
        return Response.successfulResponse("Password updated successfully");
    }


}
