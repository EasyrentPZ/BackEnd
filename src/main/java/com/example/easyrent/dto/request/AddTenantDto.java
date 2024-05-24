package com.example.easyrent.dto.request;

import lombok.Data;

@Data
public class AddTenantDto
{
    private String email;
    private String password;
    private String name;
    private String lastname;
    private Integer propertyId;
}

