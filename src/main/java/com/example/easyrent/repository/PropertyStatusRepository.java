package com.example.easyrent.repository;

import com.example.easyrent.model.PropertyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyStatusRepository extends JpaRepository<PropertyStatus, Integer>
{
}