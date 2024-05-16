package com.example.easyrent.controller;

import com.example.easyrent.dto.request.SignInRequest;
import com.example.easyrent.dto.request.SignUpRequest;
import com.example.easyrent.dto.response.JwtAuthenticationResponse;
import com.example.easyrent.dto.response.SignMessageDto;
import com.example.easyrent.service.AuthenticationService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<SignMessageDto> signup(@RequestBody SignUpRequest request)
    {
        try
        {
            authenticationService.signUp(request);
            return ResponseEntity.ok( new SignMessageDto("User registered successfully!"));
        }
        catch (DataIntegrityViolationException e)
        {
            if (e.getMessage().contains("constraint_name"))
                return new ResponseEntity<>( new SignMessageDto("Email or username already exists."), HttpStatus.INTERNAL_SERVER_ERROR);
            else
                return new ResponseEntity<>( new SignMessageDto("Invalid data"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch(IllegalArgumentException e)
        {
            return new ResponseEntity<>(new SignMessageDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(new SignMessageDto("UnknownError!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<SignMessageDto> signin(@RequestBody SignInRequest request, HttpServletResponse res)
    {
        JwtAuthenticationResponse token = authenticationService.signIn(request);
        Cookie jwtCookie = new Cookie("jwtCookie", token.getToken());
        jwtCookie.setPath("/");
        jwtCookie.setHttpOnly(true);
        jwtCookie.setMaxAge(36000);
        res.addCookie(jwtCookie);
        return ResponseEntity.ok(new SignMessageDto("Success"));
    }
}
