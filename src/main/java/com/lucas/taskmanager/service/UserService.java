package com.lucas.taskmanager.service;

import com.lucas.taskmanager.config.SecurityConfig;
import com.lucas.taskmanager.dto.UserRequest;
import com.lucas.taskmanager.dto.UserResponse;
import com.lucas.taskmanager.exception.DuplicateEmailUserException;
import com.lucas.taskmanager.model.User;
import com.lucas.taskmanager.repository.UserRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.websocket.Encoder;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // getters
    public List<UserResponse> showUser() {
        return this.userRepository.findAll().stream().map(this::toResponserUser).toList();
    }


    // Posts
    public UserResponse createUser(UserRequest request) {


        if (this.userRepository.existsByEmail(request.email())) {
            throw new DuplicateEmailUserException(request.email());
        }

        String hashedPassword = passwordEncoder.encode(request.password());

        User user = new User(request.name(), request.email(), hashedPassword);


        return toResponserUser(this.userRepository.save(user));

    }

    // config

    private UserResponse toResponserUser(User user) {

        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

    @Override
    public @Nullable UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        User dbUser = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));


        UserDetails userDetails = org.springframework.security.core.userdetails.User.
                withUsername(dbUser.getEmail()).
                password(dbUser.getPassword()).
                roles("USER").
                build();

        return userDetails;
    }
}
