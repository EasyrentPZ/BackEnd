package com.example.easyrent.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    //private String profilePicture;
    private String name;
    private String lastname;
    private String phoneNumber;
    private String email;
    private List<String> roles;
}
