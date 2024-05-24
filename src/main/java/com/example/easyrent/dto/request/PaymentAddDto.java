package com.example.easyrent.dto.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class PaymentAddDto
{
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date issueDate;
    private String title;
    private String invoiceNumber;
    private BigDecimal amount;
    private MultipartFile invoice;
}
