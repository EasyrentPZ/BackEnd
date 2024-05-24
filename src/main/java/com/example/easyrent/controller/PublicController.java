package com.example.easyrent.controller;

import com.example.easyrent.dto.response.PropertyResponseDto;
import com.example.easyrent.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/public")
public class PublicController
{
    private final PropertyService propertyService;
    @GetMapping
    public ResponseEntity<Page<PropertyResponseDto>> getAllMarketProperties()
    {
        Page<PropertyResponseDto> propertiesPage = propertyService.getAllMarketProperties();
        return ResponseEntity.ok().body(propertiesPage);
    }

    @GetMapping("/properties/{propertyId}")
    public ResponseEntity<PropertyResponseDto> getProperty(@PathVariable("propertyId") Integer propertyId)
    {
        PropertyResponseDto propertyDto = propertyService.getProperty(propertyId);
        if (propertyDto != null)
            return ResponseEntity.ok().body(propertyDto);
        else
            return ResponseEntity.notFound().build();
    }
}
