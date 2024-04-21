package com.easyrent.backend.service.mapper;

import com.easyrent.backend.repository.model.*;
import com.easyrent.backend.service.dto.*;
import com.easyrent.backend.service.dto.OwnerInfoDto;
import org.springframework.beans.BeanUtils;

import java.util.stream.Collectors;

public class PropertyMapper
{

    public static PropertyResponseDto marketMapPropertyToDto(Property property)
    {
        PropertyResponseDto propertyDto = new PropertyResponseDto();
        BeanUtils.copyProperties(property, propertyDto);

        //Map rest of the more complicated objects
        propertyDto.setOwner(mapOwnerToDto(property.getOwner()));
        propertyDto.setFeatures(property.getFeatures().stream()
                .map(PropertyMapper::mapFeaturesToDto)
                .collect(Collectors.toSet()));
        propertyDto.setCity(mapCityToDto(property.getCity()));
        propertyDto.setPhotos(property.getPropertyPhotos().stream()
                .map(PropertyMapper::mapPhotoToDto)
                .collect(Collectors.toSet()));

        return propertyDto;
    }

    private static OwnerInfoDto mapOwnerToDto(User owner)
    {
        OwnerInfoDto ownerDto = new OwnerInfoDto();
        BeanUtils.copyProperties(owner, ownerDto);
        return ownerDto;
    }

    private static FeaturesInfoDto mapFeaturesToDto(Features features)
    {
        FeaturesInfoDto featuresDTO = new FeaturesInfoDto();
        BeanUtils.copyProperties(features, featuresDTO);
        return featuresDTO;
    }

    private static CityInfoDto mapCityToDto(City city)
    {
        CityInfoDto cityDto = new CityInfoDto();
        AdministrativeRegionInfoDto regionDto = new AdministrativeRegionInfoDto();
        AdministrativeRegion reg = city.getAdministrativeRegion();
        regionDto.setId(reg.getId());
        regionDto.setRegionName(reg.getRegionName());
        regionDto.setCountry_name(reg.getCountry().getCountryName());

        cityDto.setId(city.getId());
        cityDto.setName(city.getCityName());
        cityDto.setRegion(regionDto);

        return cityDto;
    }

    private static PhotoInfoDto mapPhotoToDto(PropertyPhoto photo)
    {
        PhotoInfoDto photoDTO = new PhotoInfoDto();
        BeanUtils.copyProperties(photo, photoDTO);
        return photoDTO;
    }
}