package com.easyrent.backend.repository.pks;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PropertyFeaturePK implements Serializable {
    @Column(name = "property_id")
    private Integer propertyId;

    @Column(name = "feature_id")
    private Integer featureId;
}
