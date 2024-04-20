package com.easyrent.backend.repository.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;
@Data
@Entity
@Table(name = "country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "country_name", nullable = false, length = 50)
    private String countryName;

    @Size(max = 3)
    @NotNull
    @Column(name = "iso_code", nullable = false, length = 3)
    private String isoCode;

    @OneToMany(mappedBy = "country")
    private Set<AdministrativeRegion> administrativeRegions = new LinkedHashSet<>();

}