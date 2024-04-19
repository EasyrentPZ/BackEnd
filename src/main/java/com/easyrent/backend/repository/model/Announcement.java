package com.easyrent.backend.repository.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "announcement")
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer announcementId;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Contract property;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "issue_date")
    private Date issueDate;
}