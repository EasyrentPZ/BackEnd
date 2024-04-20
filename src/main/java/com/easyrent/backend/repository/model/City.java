package com.easyrent.backend.repository.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "administrative_region_id", nullable = false)
    private AdministrativeRegion administrativeRegion;

    @Size(max = 100)
    @NotNull
    @Column(name = "city_name", nullable = false, length = 100)
    private String cityName;

    @OneToMany(mappedBy = "city")
    private Set<Property> properties = new LinkedHashSet<>();

}