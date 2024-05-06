package com.example.easyrent.controller;

import com.example.easyrent.dto.request.PasswordChangeDto;
import com.example.easyrent.dto.request.UserUpdateDto;
import com.example.easyrent.dto.response.UserDto;
import com.example.easyrent.model.User;
import com.example.easyrent.mapper.UserMapper;
import com.example.easyrent.service.AuthenticationService;
import com.example.easyrent.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id) {
        User user = userService.findById(id);
        if (user != null) {
            UserDto userDto = UserMapper.toDto(user);
            return ResponseEntity.ok(userDto);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/roles")
    public ResponseEntity<?> getUserRolesById(@PathVariable Integer id) {
        User user = userService.findById(id);
        if (user != null) {
            return ResponseEntity.ok(user.getRoles());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody UserUpdateDto userDto) {
        boolean updated = userService.updateUser(id, userDto);
        return updated ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<?> changePassword(@PathVariable Integer id, @RequestBody PasswordChangeDto passwordDto) {
        boolean changed = authenticationService.changePassword(id, passwordDto);
        return changed ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}