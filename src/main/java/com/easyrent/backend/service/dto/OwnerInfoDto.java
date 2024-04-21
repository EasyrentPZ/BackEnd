package com.easyrent.backend.service.dto;

import lombok.Data;

@Data
public class OwnerInfoDto
{
    private Integer id;
    private String name;
    private String lastname;
    private String email;
    private String phoneNumber;
}
