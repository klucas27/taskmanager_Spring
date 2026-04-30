package com.lucas.taskmanager.repository;

import com.lucas.taskmanager.model.User;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByEmail(String email);

    List<User> findByName(String name);

    boolean existsById(@NonNull Long id);

}
