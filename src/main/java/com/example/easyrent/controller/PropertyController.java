package com.example.easyrent.controller;

import com.example.easyrent.service.PropertyService;
import com.example.easyrent.dto.response.PropertyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/property")
public class PropertyController
{
    private final PropertyService propertyService;

    @GetMapping
    public ResponseEntity<Page<PropertyResponseDto>> getAllMarketProperties()
    {
        Page<PropertyResponseDto> propertiesPage = propertyService.getAllMarketProperties();
        return ResponseEntity.ok().body(propertiesPage);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<Page<PropertyResponseDto>> getPropertiesByOwnerId(@PathVariable("ownerId") int ownerId)
    {
        System.out.println(ownerId);
        Page<PropertyResponseDto> propertiesPage = propertyService.getPropertiesByOwnerId(ownerId);
        return ResponseEntity.ok().body(propertiesPage);
    }

    @GetMapping("/owner/properties/{propertyId}")
    public ResponseEntity<PropertyResponseDto> getOwnerPropertyById(@PathVariable("propertyId") Integer propertyId)
    {
        PropertyResponseDto propertyDto = propertyService.getOwnerPropertyById(propertyId);
        if (propertyDto != null)
            return ResponseEntity.ok().body(propertyDto);
        else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePropertyById(@PathVariable("id") Integer id)
    {
        boolean deleted = propertyService.deletePropertyById(id);
        if (deleted)
            return ResponseEntity.ok().body("Property " + "deleted successfully.");
        else
            return ResponseEntity.notFound().build();
    }

}
