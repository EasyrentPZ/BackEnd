package com.example.easyrent.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "administrative_region_id", nullable = false)
    private AdministrativeRegion administrativeRegion;

    @Column(name = "city_name", nullable = false, length = 100)
    private String cityName;

    @OneToMany(mappedBy = "city")
    private Set<Property> properties = new LinkedHashSet<>();

}