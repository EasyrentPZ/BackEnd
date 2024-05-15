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
    public ResponseEntity<Page<PropertyResponseDto>> getAllMarketProperties(@CookieValue("jwtCookie") String jwtToken)
    {
        Page<PropertyResponseDto> propertiesPage = propertyService.getAllMarketProperties();
        return ResponseEntity.ok().body(propertiesPage);
    }

    @GetMapping("/owner")
    public ResponseEntity<Page<PropertyResponseDto>> getOwnerProperties(@CookieValue("jwtCookie") String jwtToken)
    {
        Page<PropertyResponseDto> propertiesPage = propertyService.getOwnerProperties(jwtToken);
        return ResponseEntity.ok().body(propertiesPage);
    }

    @GetMapping("/owner/properties/{propertyId}")
    public ResponseEntity<PropertyResponseDto> getOwnerProperty(@CookieValue("jwtCookie") String jwtToken, @PathVariable("propertyId") Integer propertyId)
    {
        PropertyResponseDto propertyDto = propertyService.getOwnerProperty(jwtToken, propertyId);
        if (propertyDto != null)
            return ResponseEntity.ok().body(propertyDto);
        else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePropertyById(@CookieValue("jwtCookie") String jwtToken, @PathVariable("id") Integer id)
    {
        boolean deleted = propertyService.deletePropertyById(jwtToken, id);
        if (deleted)
            return ResponseEntity.ok().body("Property " + "deleted successfully.");
        else
            return ResponseEntity.notFound().build();
    }

}
