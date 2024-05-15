package com.example.easyrent.dto.response;

import lombok.Data;

@Data
public class CityFilterResponseDto
{
    private Integer id;
    private String country;
    private String region;
    private String cityName;
}