package com.lucas.taskmanager.controller;

import com.lucas.taskmanager.dto.LoginRequest;
import com.lucas.taskmanager.service.JwtService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;


    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    @PostMapping("/login")
    public String getLogin(LoginRequest request) {
        return jwtService.generateToken(request.email());
    }
}
