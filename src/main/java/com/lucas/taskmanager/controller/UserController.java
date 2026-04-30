package com.lucas.taskmanager.controller;

import com.lucas.taskmanager.dto.UserRequest;
import com.lucas.taskmanager.dto.UserResponse;
import com.lucas.taskmanager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> showUsers(){
        return this.userService.showUser();
    }

    @PostMapping
    public ResponseEntity<UserResponse> newUser(@Valid @RequestBody UserRequest request){
        UserResponse created = userService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);

    }
}
