package com.example.easyrent.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest
{
    private String username;
    private String password;
    private String confirmPassword;
    private String name;
    private String lastname;
}
