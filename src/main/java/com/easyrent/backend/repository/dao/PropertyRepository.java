package com.easyrent.backend.repository.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import com.easyrent.backend.repository.model.Property;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long>
{
    List<Property> findByOwnerId(Integer ownerId);
}
