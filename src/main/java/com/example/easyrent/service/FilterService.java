package com.example.easyrent.service;

import com.example.easyrent.dto.response.CityFilterResponseDto;
import com.example.easyrent.dto.response.CityInfoDto;
import com.example.easyrent.mapper.FilterMapper;
import com.example.easyrent.mapper.PropertyMapper;
import com.example.easyrent.model.AdministrativeRegion;
import com.example.easyrent.model.City;
import com.example.easyrent.repository.AdministrativeRepository;
import com.example.easyrent.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilterService
{
    private final CityRepository cityRepository;
    private final AdministrativeRepository administrativeRepository;

    public List<CityFilterResponseDto> getAllPropertiesFilter()
    {
        List<City> cities = cityRepository.findAll();
        List<CityFilterResponseDto> response = new ArrayList<>();
        for(City city: cities)
        {
            CityFilterResponseDto tmp = FilterMapper.mapCityToDto(city);
            response.add(tmp);
        }
        return response;
    }

    public List<CityFilterResponseDto> getAllCitiesInCountry(String countryName)
    {
        List<City> cities = new LinkedList<>();
        List<CityFilterResponseDto> response = new ArrayList<>();
        List<AdministrativeRegion> regions = administrativeRepository.getAdministrativeRegionsByCountry_CountryName(countryName);
        for(AdministrativeRegion region: regions)
            cities.addAll(region.getCities());

        for(City city: cities)
        {
            CityFilterResponseDto tmp = FilterMapper.mapCityToDto(city);
            response.add(tmp);
        }
        return response;
    }
}
