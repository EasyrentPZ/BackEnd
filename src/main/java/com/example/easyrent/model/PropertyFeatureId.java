package com.example.easyrent.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class PropertyFeatureId implements Serializable {
    private static final long serialVersionUID = -971292559998212782L;
    @Column(name = "property_id", nullable = false)
    private Integer propertyId;

    @Column(name = "feature_id", nullable = false)
    private Integer featureId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PropertyFeatureId entity = (PropertyFeatureId) o;
        return Objects.equals(this.propertyId, entity.propertyId) &&
                Objects.equals(this.featureId, entity.featureId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(propertyId, featureId);
    }

}