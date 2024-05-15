package com.example.easyrent.controller;

import com.example.easyrent.dto.response.CityFilterResponseDto;
import com.example.easyrent.service.FilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        List<CityFilterResponseDto> propertiesPage = filterService.getAllPropertiesFilter();
        return ResponseEntity.ok().body(propertiesPage);
    }
}
