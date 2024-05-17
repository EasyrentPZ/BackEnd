package com.example.easyrent.repository;

import com.example.easyrent.model.City;
import com.example.easyrent.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer>
{
    List<City> findAll();
    City findCityByCityName(String name);

}
