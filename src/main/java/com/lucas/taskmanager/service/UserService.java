package com.lucas.taskmanager.service;

import com.lucas.taskmanager.dto.UserRequest;
import com.lucas.taskmanager.dto.UserResponse;
import com.lucas.taskmanager.exception.UserNotFoundException;
import com.lucas.taskmanager.model.User;
import com.lucas.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    // getters
    public List<UserResponse> showUser(){
        return this.userRepository.findAll().stream().map(this::toResponserUser).toList();
    }


    // Posts
    public UserResponse createUser(UserRequest request) {

//        if (userRepository.existsById(userRepository.exists() .userId())) {
//            throw new UserNotFoundException(request.userId());
//        }

        User user = new User(request.name(), request.email());

        return toResponserUser(this.userRepository.save(user));

    }

    // config

    private UserResponse toResponserUser(User user) {

        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }
}
