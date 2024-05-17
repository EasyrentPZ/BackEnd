package com.example.easyrent.controller;

import com.example.easyrent.dto.response.CityFilterResponseDto;
import com.example.easyrent.dto.response.MessageDto;
import com.example.easyrent.service.FilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/filters")
public class FilterController
{
    private final FilterService filterService;
    @GetMapping("/properties")
    public ResponseEntity<List<CityFilterResponseDto>> getAllPropertiesFilter(@CookieValue("jwtCookie") String jwtToken)
    {
        try
        {
            List<CityFilterResponseDto> propertiesList = filterService.getAllPropertiesFilter();
            return ResponseEntity.ok().body(propertiesList);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/country/{countryName}")
    public ResponseEntity<List<CityFilterResponseDto>> getAllCitiesForCountry(@CookieValue("jwtCookie") String jwtToken, @PathVariable("countryName") String countryName)
    {
        try
        {
            List<CityFilterResponseDto> citiesInCountry = filterService.getAllCitiesInCountry(countryName);
            return ResponseEntity.ok(citiesInCountry);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
