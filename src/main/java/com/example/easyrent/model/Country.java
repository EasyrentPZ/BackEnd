package com.example.easyrent.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id", nullable = false)
    private Integer id;

    @Column(name = "country_name", nullable = false, length = 50)
    private String countryName;

    @Column(name = "iso_code", nullable = false, length = 3)
    private String isoCode;

    @OneToMany(mappedBy = "country")
    private Set<AdministrativeRegion> administrativeRegions = new LinkedHashSet<>();

}