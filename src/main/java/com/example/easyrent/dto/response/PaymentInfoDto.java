package com.example.easyrent.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentInfoDto
{
    private Integer id;
    private LocalDate issueDate;
    private String paymentStatus;
    private String title;
    private String invoiceNumber;
    private BigDecimal amount;
}
