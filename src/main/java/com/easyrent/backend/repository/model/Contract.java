package com.easyrent.backend.repository.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "contract")
@ToString(exclude = "property")
public class Contract
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Size(max = 512)
    @Column(name = "notes", length = 512)
    private String notes;

    @OneToMany(mappedBy = "contract")
    private Set<Announcement> announcements = new LinkedHashSet<>();

    @OneToMany(mappedBy = "contract")
    private Set<Payment> payments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "contract")
    private Set<Ticket> tickets = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "user_contract",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "contract_id"))
    private Set<User> users = new LinkedHashSet<>();

}