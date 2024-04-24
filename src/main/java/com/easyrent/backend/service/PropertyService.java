package com.easyrent.backend.service;

import com.easyrent.backend.repository.dao.PropertyRepository;
import com.easyrent.backend.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import com.easyrent.backend.repository.model.Property;

import com.easyrent.backend.service.mapper.PropertyMapper;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyService
{
    private final PropertyRepository propertyRepository;
    public Page<PropertyResponseDto> getAllMarketProperties()
    {
        List<Property> properties = propertyRepository.findByPropertyStatusId(2);
        List<PropertyResponseDto> dto = properties.stream()
                .map(PropertyMapper::marketMapPropertyToDto)
                .collect(Collectors.toList());
        return new PageImpl<>(dto);
    }

    public Page<PropertyResponseDto> getPropertiesByOwnerId(Integer ownerId)
    {
        List<Property> properties = propertyRepository.findByOwnerId(ownerId);
        List<PropertyResponseDto> dto = properties.stream()
                .map(PropertyMapper::marketMapPropertyToDto)
                .collect(Collectors.toList());
        return new PageImpl<>(dto);
    }

    public PropertyResponseDto getOwnerPropertyById(Integer id)
    {
        Property property = propertyRepository.findById(Long.valueOf(id)).orElse(null);
        return property != null ? PropertyMapper.marketMapPropertyToDto(property) : null;
    }

    public boolean deletePropertyById(Integer id)
    {
        try
        {
            propertyRepository.deleteById(Long.valueOf(id));
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
