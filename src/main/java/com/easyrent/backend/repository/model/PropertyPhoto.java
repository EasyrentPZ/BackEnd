package com.easyrent.backend.repository.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "property_photo")
public class PropertyPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @NotNull
    @Column(name = "is_main", nullable = false)
    private Boolean isMain = false;

    @Size(max = 256)
    @NotNull
    @Column(name = "photo", nullable = false, length = 256)
    private String photo;

}