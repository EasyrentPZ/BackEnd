package com.easyrent.backend.service;

import com.easyrent.backend.repository.dao.PropertyRepository;
import com.easyrent.backend.repository.model.AdministrativeRegion;
import com.easyrent.backend.repository.model.User;
import com.easyrent.backend.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import com.easyrent.backend.repository.model.Property;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyService
{
    private final PropertyRepository propertyRepository;
    public Page<PropertyResponseDto> getAllProperties()
    {
        List<Property> properties = propertyRepository.findAll();
        List<PropertyResponseDto> dto = properties.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new PageImpl<>(dto);
    }

    private PropertyResponseDto convertToDto(Property property)
    {
        PropertyResponseDto dto = new PropertyResponseDto();
        dto.setId(property.getId());
        dto.setAddress(property.getAddress());
        dto.setArea(property.getArea());
        dto.setDescription(property.getDescription());
        dto.setPets(property.getPets());
        dto.setRentAmount(property.getRentAmount());
        dto.setUtilityCost(property.getUtilityCost());
        dto.setDeposit(property.getDeposit());
        dto.setStreetName(property.getStreetName());

        // Convert information about owner to dto
        OwnerInfoDto ownerDto = new OwnerInfoDto();
        User owner = property.getOwner();
        ownerDto.setId(owner.getId());
        ownerDto.setName(owner.getName());
        ownerDto.setLastName(owner.getLastname());
        ownerDto.setEmail(owner.getEmail());
        ownerDto.setPhoneNumber(owner.getPhoneNumber());
        dto.setOwner(ownerDto);

        Set<FeaturesInfoDto> featuresDto = property.getFeatures().stream()
                .map(feature -> {
                    FeaturesInfoDto featureDto = new FeaturesInfoDto();
                    featureDto.setId(feature.getFeatureId());
                    featureDto.setName(feature.getName());
                    return featureDto;
                })
                .collect(Collectors.toSet());
        dto.setFeatures(featuresDto);

        // Convert city to dto
        AdministrativeRegionInfoDto regionDto = new AdministrativeRegionInfoDto();
        AdministrativeRegion reg = property.getCity().getAdministrativeRegion();
        regionDto.setId(reg.getId());
        regionDto.setRegionName(reg.getRegionName());
        regionDto.setCountry_name(reg.getCountry().getCountryName());
        CityInfoDto cityDto = new CityInfoDto();
        cityDto.setId(property.getCity().getId());
        cityDto.setName(property.getCity().getCityName());
        cityDto.setRegion(regionDto);
        dto.setCity(cityDto);

        //Convert photos to dto
        Set<PhotoInfoDto> photosDto = property.getPropertyPhotos().stream()
                .map(photo -> {
                    PhotoInfoDto photoDto = new PhotoInfoDto();
                    photoDto.setId(photo.getId());
                    photoDto.setLink(photo.getPhoto());
                    photoDto.setIsMain(photo.getIsMain());
                    return photoDto;
                })
                .collect(Collectors.toSet());
        dto.setFeatures(featuresDto);


        return dto;
    }
}
