package com.easyrent.backend.service.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class PropertyResponseDto
{
    private Integer id;
    private OwnerInfoDto owner;
    private String address;
    private Double area;
    private String description;
    private Boolean pets;
    private Set<FeaturesInfoDto> features;
    private CityInfoDto city;
    private BigDecimal rentAmount;
    private BigDecimal utilityCost;
    private BigDecimal deposit;
    private String streetName;
    private Set<PhotoInfoDto> photos;
}
