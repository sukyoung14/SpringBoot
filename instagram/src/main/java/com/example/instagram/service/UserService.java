package com.example.instagram.service;

import com.example.instagram.dto.SignUpRequest;
import com.example.instagram.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public interface UserService {
    User register(SignUpRequest signUpRequest);
    boolean existsByUsername(String username);
}
