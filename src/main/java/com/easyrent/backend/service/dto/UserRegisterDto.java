package com.easyrent.backend.service.dto;

import lombok.Data;

@Data
public class UserRegisterDto {
    private String name;
    private String lastName;
    private String email;
    private String password;
    private RequestedRole role;
    private String phoneNumber;
    private String bankAccount;
}