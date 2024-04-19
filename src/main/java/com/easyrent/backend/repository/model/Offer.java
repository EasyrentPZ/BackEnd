package com.easyrent.backend.repository.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "offer")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer offerId;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @Column(name = "rent_amount_money")
    private Double rentAmountMoney;

    @Column(name = "utility_cost_money")
    private Double utilityCostMoney;

    @Column(name = "deposit_money")
    private Double depositMoney;

}