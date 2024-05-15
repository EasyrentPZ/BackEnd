package com.example.easyrent.service;

import com.example.easyrent.dto.response.CityFilterResponseDto;
import com.example.easyrent.dto.response.CityInfoDto;
import com.example.easyrent.mapper.FilterMapper;
import com.example.easyrent.mapper.PropertyMapper;
import com.example.easyrent.model.City;
import com.example.easyrent.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilterService
{
    private final CityRepository cityRepository;

    public List<CityFilterResponseDto> getAllPropertiesFilter()
    {
        List<City> cities = cityRepository.findAll();
        List<CityFilterResponseDto> response = new ArrayList<>();
        for(City x: cities)
        {
            CityFilterResponseDto tmp = FilterMapper.mapCityToDto(x);
            response.add(tmp);
        }
        return response;
    }
}
