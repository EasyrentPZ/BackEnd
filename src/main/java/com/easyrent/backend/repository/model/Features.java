package com.easyrent.backend.repository.model;

import com.easyrent.backend.repository.enums.FeaturesList;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(exclude = "properties")
@ToString(exclude = "properties")
@Entity
@Table(name = "features")
public class Features
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feature_id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "features")
    private Set<Property> properties = new LinkedHashSet<>();

}
