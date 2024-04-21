package com.easyrent.backend.web.controllers;

import com.easyrent.backend.repository.model.Property;
import com.easyrent.backend.service.PropertyService;
import com.easyrent.backend.service.dto.PropertyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<Page<PropertyResponseDto>> getPropertiesByOwnerId(@PathVariable Integer ownerId)
    {
        Page<PropertyResponseDto> propertiesPage = propertyService.getPropertiesByOwnerId(ownerId);
        return ResponseEntity.ok().body(propertiesPage);
    }

    @GetMapping("/owner/properties/{propertyId}")
    public ResponseEntity<PropertyResponseDto> getOwnerPropertyById(@PathVariable Integer propertyId)
    {
        PropertyResponseDto propertyDto = propertyService.getOwnerPropertyById(propertyId);
        if (propertyDto != null)
            return ResponseEntity.ok().body(propertyDto);
        else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePropertyById(@PathVariable Integer id)
    {
        boolean deleted = propertyService.deletePropertyById(id);
        if (deleted)
            return ResponseEntity.ok().body("Property " + "deleted successfully.");
        else
            return ResponseEntity.notFound().build();
    }

}
