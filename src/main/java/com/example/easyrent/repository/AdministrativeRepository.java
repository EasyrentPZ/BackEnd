package com.example.easyrent.repository;

import com.example.easyrent.model.AdministrativeRegion;
import com.example.easyrent.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdministrativeRepository extends JpaRepository<AdministrativeRegion, Integer>
{
    List<AdministrativeRegion> getAdministrativeRegionsByCountry_CountryName(String countryName);

}
