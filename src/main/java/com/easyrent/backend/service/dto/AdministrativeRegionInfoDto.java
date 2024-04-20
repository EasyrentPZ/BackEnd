package com.easyrent.backend.service.dto;

import com.easyrent.backend.repository.model.Country;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AdministrativeRegionInfoDto
{
    private Integer id;
    private String country_name;
    private String regionName;
}
