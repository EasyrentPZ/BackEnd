package com.example.easyrent.service;

import com.example.easyrent.model.Role;
import com.example.easyrent.model.User;
import com.example.easyrent.repository.*;
import com.example.easyrent.dto.request.*;
import com.example.easyrent.dto.response.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthenticationService
{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public JwtAuthenticationResponse signUp(SignUpRequest request)
    {
        // Check if the email already exists
        if (userRepository.existsByUsername(request.getUsername()))
            throw new IllegalArgumentException("Account with this email already exists!");

        // Validate email format
        if (!isValidEmail(request.getUsername()))
            throw new IllegalArgumentException("Invalid email format!");

        // Validate password complexity
        if (!isValidPassword(request.getPassword()))
            throw new IllegalArgumentException("Password does not meet the requirements!");

        // Check if both passwords match
        if (!request.getPassword().equals(request.getConfirmPassword()))
            throw new IllegalArgumentException("Passwords do not match!");

        // Fetch default account type
        Role defaultAccountType = roleRepository.findByName("OWNER");

        // Hash the password
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // Create a new user
        User user = User.builder()
                .username(request.getUsername())
                .password(encodedPassword)
                .roles(Set.of(defaultAccountType))
                .name(request.getName())
                .lastname(request.getLastname())
                .build();

        // Save the user to the database
        userRepository.save(user);

        // Generate JWT token
        String token = jwtService.generateToken(user);

        // Return the JWT authentication response
        return JwtAuthenticationResponse.builder()
                .token(token)
                .build();
    }
    public boolean changePassword(Integer id, PasswordChangeDto passwordDto) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null && passwordEncoder.matches(passwordDto.getCurrentPassword(), user.getPassword())) {
            if(!isValidPassword(passwordDto.getNewPassword())) {
                throw new IllegalArgumentException("Password does not meet the requirements!");
            }
            user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
            userRepository.save(user);
            return true;
        }
        return false;
    }
    // Validate email format
    private boolean isValidEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.matches(emailRegex, email);
    }

    // Validate password complexity
    private boolean isValidPassword(String password)
    {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        return Pattern.matches(passwordRegex, password);
    }

    public JwtAuthenticationResponse signIn(SignInRequest request)
    {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        UserDetails userDetails = loadUserByUsername(request.getUsername());

        String token = jwtService.generateToken(userDetails);

        return JwtAuthenticationResponse.builder()
                .token(token)
                .build();
    }

    private UserDetails loadUserByUsername(String username)
    {
        return new org.springframework.security.core.userdetails.User(
                username,
                userRepository.findByUsername(username)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"))
                        .getPassword(),
                true,
                true,
                true,
                true,
                AuthorityUtils.createAuthorityList("USER")
        );
    }
}
