package com.easyrent.backend.service.dto;

import lombok.Data;

@Data
public class AdministrativeRegionInfoDto
{
    private Integer id;
    private String regionName;

    private CountryInfoDto countryInfoDto;
}
