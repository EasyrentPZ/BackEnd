package com.example.easyrent.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "payment")
public class Payment
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payment_status_id", nullable = false)
    private PaymentStatus paymentStatus;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Column(name = "invoice_number", length = 100)
    private String invoiceNumber;

    @Column(name = "bill_pdf")
    private byte[] billPdf;

    @Column(name = "amount")
    private BigDecimal amount;

}