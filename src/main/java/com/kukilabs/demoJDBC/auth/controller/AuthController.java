package com.kukilabs.demoJDBC.auth.controller;

import com.kukilabs.demoJDBC.auth.dto.LoginRequestDto;
import com.kukilabs.demoJDBC.auth.entity.UserAuth;
import com.kukilabs.demoJDBC.auth.service.AuthService;
import com.kukilabs.demoJDBC.responses.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Validated
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager){
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequestDto userLogin){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserAuth userDetails = (UserAuth) authentication.getPrincipal();
        String token = authService.generateToken(authentication);
        return Response.successfulResponse("login success", token);
    }

}
