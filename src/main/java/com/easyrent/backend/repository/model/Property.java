package com.easyrent.backend.repository.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer propertyId;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private PropertyStatus status;

    @Column(name = "address")
    private String address;

    @Column(name = "area")
    private Double area;

    @Column(name = "description")
    private String description;

    @Column(name = "features")
    private String features;

    @OneToMany(mappedBy = "property")
    private List<Announcement> announcements;

    @OneToMany(mappedBy = "feature")
    private Set<PropertyFeatures> employerDeliveryAgent = new HashSet<PropertyFeatures>();

}