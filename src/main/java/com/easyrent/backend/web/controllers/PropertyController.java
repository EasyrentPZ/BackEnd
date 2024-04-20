package com.easyrent.backend.web.controllers;

import com.easyrent.backend.service.PropertyService;
import com.easyrent.backend.service.dto.PropertyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/easyrent-api/v2/property")
public class PropertyController
{
    private final PropertyService propertyService;

    @GetMapping
    public ResponseEntity<Page<PropertyResponseDto>> getAllProperties()
    {
        Page<PropertyResponseDto> propertiesPage = propertyService.getAllProperties();
        return ResponseEntity.ok().body(propertiesPage);
    }
}
