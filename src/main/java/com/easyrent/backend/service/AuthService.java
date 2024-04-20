package com.easyrent.backend.service;

import com.easyrent.backend.repository.dao.UserRepository;
import com.easyrent.backend.repository.model.User;
import com.easyrent.backend.service.dto.UserLoginDto;
import com.easyrent.backend.service.dto.UserRegisterDto;
import com.easyrent.backend.service.exception.UserAlreadyExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.Set;

@Component
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleService roleService;


    public String authenticateUser(UserLoginDto userLoginDto) {
        return userLoginDto.getUsername();
    }

    @Transactional
    public Integer registerUser(UserRegisterDto request) throws UserAlreadyExistsException {
        if (userService.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(String.format("User with email %s already exists.", request.getEmail()));
        }

        User user = new User();
        user.setName(request.getName());
        user.setLastname(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRoles(Set.of(roleService.findRoleByName(request.getRole().name())));
        //user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(user).getId();
    }


}
