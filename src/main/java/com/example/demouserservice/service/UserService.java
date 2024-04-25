package com.example.demouserservice.service;

import com.example.demouserservice.domain.User;
import com.example.demouserservice.dto.UserDTO;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    List<User> findAll();

    List<User> findAll(LocalDate from, LocalDate to);

    User findById(Long id);

    User create(User user);

    User partialUpdate(Long id, UserDTO user);

    User fullyUpdate(Long id, User user);

    void deleteById(Long userId);
}
