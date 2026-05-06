package com.lucas.taskmanager.controller;

import com.lucas.taskmanager.config.SecurityConfig;
import com.lucas.taskmanager.dto.LoginRequest;
import com.lucas.taskmanager.service.JwtService;
import com.lucas.taskmanager.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(JwtService jwtService, UserService userService, SecurityConfig securityConfig, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/login")
    public String getLogin(@RequestBody LoginRequest request) {

        UserDetails userDetail = userService.loadUserByUsername(request.email());

        if (!passwordEncoder.matches(request.password(), userDetail.getPassword())){
            throw new RuntimeException("Invalid credentials");
        }


        return jwtService.generateToken(request.email());
    }
}
