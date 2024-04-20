package com.easyrent.backend.service.dto;

import com.easyrent.backend.repository.enums.FeaturesList;
import lombok.Data;

@Data
public class FeaturesInfoDto
{
    private Integer id;
    private FeaturesList name;
}
