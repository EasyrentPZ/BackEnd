package com.example.easyrent.dto.response;

import lombok.Data;

@Data
public class AdministrativeRegionInfoDto
{
    private Integer id;
    private String regionName;

    private CountryInfoDto countryInfoDto;
}
