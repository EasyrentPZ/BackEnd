package com.example.easyrent.mapper;

import com.example.easyrent.dto.response.*;
import com.example.easyrent.model.*;

public class FilterMapper
{

    public static CityFilterResponseDto mapCityToDto(City city)
    {
        CityFilterResponseDto cityDto = new CityFilterResponseDto();
        AdministrativeRegion reg = city.getAdministrativeRegion();

        cityDto.setId(city.getId());
        cityDto.setCityName(city.getCityName());
        cityDto.setRegion(reg.getRegionName());
        cityDto.setCountry(reg.getCountry().getCountryName());

        return cityDto;
    }

}
