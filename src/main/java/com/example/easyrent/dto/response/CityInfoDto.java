package com.example.easyrent.dto.response;

import lombok.Data;

@Data
public class CityInfoDto
{
    private Integer id;
    private AdministrativeRegionInfoDto region;
    private String name;
}
