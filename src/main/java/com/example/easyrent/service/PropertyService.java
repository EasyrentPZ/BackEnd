package com.example.easyrent.service;

import com.example.easyrent.mapper.PropertyMapper;
import com.example.easyrent.model.Property;
import com.example.easyrent.model.User;
import com.example.easyrent.repository.PropertyRepository;
import com.example.easyrent.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyService
{
    private final PropertyRepository propertyRepository;
    private final UserService userService;
    public Page<PropertyResponseDto> getAllMarketProperties()
    {
        List<Property> properties = propertyRepository.findByPropertyStatusId(2);
        List<PropertyResponseDto> dto = properties.stream()
                .map(PropertyMapper::marketMapPropertyToDto)
                .collect(Collectors.toList());
        return new PageImpl<>(dto);
    }

    public Page<PropertyResponseDto> getOwnerProperties(String jwtToken)
    {
        User currentUser = userService.getUserFromToken(jwtToken);
        List<Property> properties = propertyRepository.findByOwnerId(currentUser.getId());
        List<PropertyResponseDto> dto = properties.stream()
                .map(PropertyMapper::marketMapPropertyToDto)
                .collect(Collectors.toList());
        return new PageImpl<>(dto);
    }

    public PropertyResponseDto getOwnerProperty(String jwtToken, Integer propertyId)
    {
        User currentUser = userService.getUserFromToken(jwtToken);
        Property property = propertyRepository.findById(propertyId).orElse(null);
        if(property != null && currentUser.getProperties().contains(property))
            return PropertyMapper.marketMapPropertyToDto(property);
        return null;
    }

    public boolean deletePropertyById(String jwtToken, Integer id)
    {
        try
        {
            User currentUser = userService.getUserFromToken(jwtToken);
            Property property = propertyRepository.findById(id).orElse(null);
            if(property != null && currentUser.getProperties().contains(property))
            {
                propertyRepository.deleteById(id);
                return true;
            }
            return false;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
