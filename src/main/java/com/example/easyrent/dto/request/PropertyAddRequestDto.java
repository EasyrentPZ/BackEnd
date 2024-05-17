package com.example.easyrent.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyAddRequestDto
{
    private String address;
    private Double area;
    private String description;
    private Boolean pets;
    private String city;
    private BigDecimal rentAmount;
    private BigDecimal utilityCost;
    private BigDecimal deposit;
    private String streetName;
    private Integer livingRooms;
    private Set<String> features;
    private List<MultipartFile> images;
}
