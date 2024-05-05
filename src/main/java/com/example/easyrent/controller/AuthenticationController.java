package com.example.easyrent.controller;

import com.example.easyrent.dto.request.SignInRequest;
import com.example.easyrent.dto.request.SignUpRequest;
import com.example.easyrent.dto.response.JwtAuthenticationResponse;
import com.example.easyrent.service.AuthenticationService;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController
{
    private final AuthenticationService authenticationService;
    @PostMapping("/signup")
    public String signup(@RequestBody SignUpRequest request)
    {
        try
        {
            authenticationService.signUp(request);
            return "User registered successfully!";
        }
        catch (DataIntegrityViolationException e)
        {
            if (e.getMessage().contains("constraint_name"))
                return "Email or username already exists.";
             else
                return "Invalid data";
        }
        catch(IllegalArgumentException e)
        {
            return e.getMessage();
        }
        catch(Exception e)
        {
            return "Unknown error!";
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest request)
    {
        return ResponseEntity.ok(authenticationService.signIn(request));
    }
}
