package com.example.easyrent.repository;

import com.example.easyrent.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface FeatureRepository extends JpaRepository<Feature, Integer>
{
    Set<Feature> getFeaturesByNameIn(Set<String> names);
}
