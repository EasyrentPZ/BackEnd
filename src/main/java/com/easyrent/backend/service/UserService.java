package com.easyrent.backend.service;

import com.easyrent.backend.repository.dao.UserRepository;
import com.easyrent.backend.repository.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final RoleService roleservice;
    private final UserRepository userRepository;
    //private final PasswordEncoder passwordEncoder;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


}