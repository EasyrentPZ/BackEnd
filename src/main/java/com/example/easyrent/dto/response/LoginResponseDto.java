package com.example.easyrent.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto
{
    private String message;
    private Integer user_id;
}
