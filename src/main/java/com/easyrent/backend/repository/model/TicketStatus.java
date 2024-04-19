package com.easyrent.backend.repository.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "ticket_status")
public class TicketStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer statusId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "status")
    private Set<Ticket> tickets;
}