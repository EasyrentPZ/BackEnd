package com.example.easyrent.controller;

import com.example.easyrent.dto.response.MultivalueStringResponseDto;
import com.example.easyrent.service.AuthenticationService;
import com.example.easyrent.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RolesController
{
    private final AuthenticationService authenticationService;
    private final RoleService roleService;
    @GetMapping("/get")
    public ResponseEntity<MultivalueStringResponseDto> signup(@CookieValue("jwtCookie") String jwtToken)
    {
        try
        {
            MultivalueStringResponseDto responseDto = roleService.getRoles(jwtToken);
            return ResponseEntity.ok(responseDto);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
