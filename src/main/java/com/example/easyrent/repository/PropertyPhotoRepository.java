package com.example.easyrent.repository;

import com.example.easyrent.model.PropertyPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyPhotoRepository extends JpaRepository<PropertyPhoto, Integer>
{
    PropertyPhoto getPropertyPhotoById(Integer id);
}