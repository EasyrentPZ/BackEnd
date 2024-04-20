package com.easyrent.backend.service.dto;

import com.easyrent.backend.repository.model.AdministrativeRegion;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CityInfoDto
{
    private Integer id;
    private AdministrativeRegionInfoDto region;
    private String name;
}
