package com.example.easyrent.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class UserUpdateDto {
    private String name;
    private String lastname;
    private String phoneNumber;
    private String email;
    private List<String> roles;
}