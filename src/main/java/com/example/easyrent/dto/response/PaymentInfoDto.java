package com.example.easyrent.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentInfoDto
{
    private Integer paymentId;
    private LocalDate issueDate;
    private String paymentStatus;
}
