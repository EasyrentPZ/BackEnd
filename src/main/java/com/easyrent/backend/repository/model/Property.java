package com.easyrent.backend.repository.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Data
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

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Size(max = 100)
    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "area")
    private Double area;

    @Size(max = 200)
    @Column(name = "description", length = 200)
    private String description;

    @Column(name = "pets")
    private Boolean pets;

    @ManyToMany(cascade = {CascadeType.ALL},
            fetch = FetchType.EAGER)
    @JoinTable(
            name = "property_features",
            joinColumns = @JoinColumn(name = "property_id"),
            inverseJoinColumns = @JoinColumn(name = "feature_id")
    )
    private Set<Features> features = new LinkedHashSet<>();

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @NotNull
    @Column(name = "rent_amount", nullable = false)
    private BigDecimal rentAmount;

    @NotNull
    @Column(name = "utility_cost", nullable = false)
    private BigDecimal utilityCost;

    @Column(name = "living_rooms", nullable = false)
    private Integer livingRooms;

    @NotNull
    @Column(name = "deposit", nullable = false)
    private BigDecimal deposit;

    @Size(max = 100)
    @Column(name = "street_name", length = 100)
    private String streetName;

    @OneToOne(mappedBy = "property",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Contract contract;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<PropertyPhoto> propertyPhotos = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_status_id")
    private PropertyStatus propertyStatus;
}