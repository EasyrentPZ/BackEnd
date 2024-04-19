package com.easyrent.backend.repository.model;

import com.easyrent.backend.repository.pks.PropertyFeaturePK;
import jakarta.persistence.*;

@Entity
@Table(name = "property_features")
@IdClass(PropertyFeaturePK.class)
public class PropertyFeatures{
    @Id
    @ManyToOne
    @JoinColumn(name = "property_id", referencedColumnName = "property_id")
    private Property property;

    @Id
    @ManyToOne
    @JoinColumn(name = "feature_id", referencedColumnName = "feature_id")
    private Features feature;

    @Column(name = "value")
    private String value;
}
