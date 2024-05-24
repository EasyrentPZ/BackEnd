package com.example.easyrent.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "property")
public class Property
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_status_id", nullable = false)
    private PropertyStatus propertyStatus;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "area")
    private Double area;

    @Column(name = "description", length = 200)
    private String description;

    @Column(name = "pets")
    private Boolean pets;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Column(name = "rent_amount", nullable = false)
    private BigDecimal rentAmount;

    @Column(name = "utility_cost", nullable = false)
    private BigDecimal utilityCost;

    @Column(name = "deposit", nullable = false)
    private BigDecimal deposit;

    @Column(name = "street_name", length = 100)
    private String streetName;

    @Column(name = "living_rooms")
    private Integer livingRooms;

    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private Contract contract;

    @ManyToMany
    @JoinTable(
            name = "property_features",
            joinColumns = @JoinColumn(name = "property_id"),
            inverseJoinColumns = @JoinColumn(name = "feature_id")
    )
    private Set<Feature> features = new LinkedHashSet<>();

    @OneToMany(mappedBy = "property", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<PropertyPhoto> propertyPhotos = new LinkedHashSet<>();

}