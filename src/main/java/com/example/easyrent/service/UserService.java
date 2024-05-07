package com.example.easyrent.service;

import com.example.easyrent.dto.request.PasswordChangeDto;
import com.example.easyrent.dto.request.UserUpdateDto;
import com.example.easyrent.repository.UserRepository;
import com.example.easyrent.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService
{
    private final UserRepository userRepository;
    private final JwtService jwtService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        Optional<User> userOptional = userRepository.findByUsername(email);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        Collection<? extends GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return org.springframework.security.core.userdetails.User
                .withUsername(email)
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }

    public User getUserFromToken(String token)
    {
        String email = jwtService.extractEmail(token);
        Optional<User> optionalUser = userRepository.findByUsername(email);
        return optionalUser.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public boolean updateUser(Integer id, UserUpdateDto userDto) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setName(userDto.getName());
            user.setLastname(userDto.getLastname());
            user.setPhoneNumber(userDto.getPhoneNumber());
            user.setUsername(userDto.getEmail());
            userRepository.save(user);
            return true;
        }
        return false;
    }



}