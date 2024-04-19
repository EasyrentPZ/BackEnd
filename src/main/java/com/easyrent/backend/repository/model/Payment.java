package com.easyrent.backend.repository.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentId;

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @Column(name = "amount_money")
    private Double amountMoney;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "bill_pdf")
    private byte[] billPdf;

}