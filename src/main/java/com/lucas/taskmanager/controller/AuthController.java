package com.lucas.taskmanager.controller;

import com.lucas.taskmanager.config.SecurityConfig;
import com.lucas.taskmanager.dto.LoginRequest;
import com.lucas.taskmanager.service.JwtService;
import com.lucas.taskmanager.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final UserService userService;
    private final SecurityConfig securityConfig;

    public AuthController(JwtService jwtService, UserService userService, SecurityConfig securityConfig) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.securityConfig = securityConfig;
    }


    @PostMapping("/login")
    public String getLogin(@RequestBody LoginRequest request) {

        UserDetails userDetail = userService.loadUserByUsername(request.email());

        if (!securityConfig.passwordEncoder().matches(request.password(), userDetail.getPassword())){
            throw new RuntimeException("Invalid credentials");
        }


        return jwtService.generateToken(request.email());
    }
}
