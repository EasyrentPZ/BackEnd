package com.example.easyrent.repository;

import com.example.easyrent.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer>
{
    List<Property> findByOwnerId(Integer ownerId);
    List<Property> findByPropertyStatusId(Integer propertyStatusId);
}
