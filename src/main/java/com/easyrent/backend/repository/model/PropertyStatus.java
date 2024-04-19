package com.easyrent.backend.repository.model;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "property_status")
public class PropertyStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer statusId;

    @Column(name = "name")
    private String name;

    // Assuming a one-to-many relationship with Property
    @OneToMany(mappedBy = "status")
    private Set<Property> properties;
}