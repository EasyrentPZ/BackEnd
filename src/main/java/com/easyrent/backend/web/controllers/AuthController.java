package com.easyrent.backend.web.controllers;

import com.easyrent.backend.service.AuthService;
import com.easyrent.backend.service.dto.UserLoginDto;
import com.easyrent.backend.service.dto.UserRegisterDto;
import com.easyrent.backend.service.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatusCode;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/easyrent-api/v2")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto) {
        return null;
    }

    @PostMapping("/register_owner")
    public ResponseEntity<?> registerOwner(@RequestBody UserRegisterDto userRegisterDto) throws UserAlreadyExistsException {
        authService.registerUser(userRegisterDto);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).build();
    }

    @PostMapping("/register_tenant")
    public ResponseEntity<?> registerTenant(@RequestBody UserRegisterDto userRegisterDto) throws UserAlreadyExistsException {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(new HashMap<>() {{
            put("id", authService.registerUser(userRegisterDto));
        }});
    }
}