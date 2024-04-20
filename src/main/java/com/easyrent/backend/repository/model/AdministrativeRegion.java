package com.easyrent.backend.repository.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "administrative_region")
public class AdministrativeRegion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "administrative_region_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @Size(max = 100)
    @NotNull
    @Column(name = "region_name", nullable = false, length = 100)
    private String regionName;

    @OneToMany(mappedBy = "administrativeRegion")
    private Set<City> cities = new LinkedHashSet<>();

}