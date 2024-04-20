package com.easyrent.backend.repository.model;

import com.easyrent.backend.repository.enums.FeaturesList;
import jakarta.persistence.*;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "features")
public class Features {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer featureId;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private FeaturesList name;

    @ManyToMany(mappedBy = "features")
    private Set<Property> properties = new LinkedHashSet<>();


}
