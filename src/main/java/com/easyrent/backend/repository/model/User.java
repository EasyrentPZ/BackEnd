package com.easyrent.backend.repository.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "\"user\"")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Size(max = 100)
    @NotNull
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 100)
    @NotNull
    @Column(name = "lastname", nullable = false, length = 100)
    private String lastname;

    @Size(max = 100)
    @Column(name = "phone_number", length = 100)
    private String phoneNumber;

    @Size(max = 100)
    @Column(name = "bank_account", length = 100)
    private String bankAccount;

    @OneToMany(mappedBy = "owner")
    private Set<Property> properties = new LinkedHashSet<>();

    @OneToMany(mappedBy = "notifier")
    private Set<Ticket> tickets = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "users")
    private Set<Contract> contracts = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "users")
    private Set<Role> roles = new LinkedHashSet<>();

}