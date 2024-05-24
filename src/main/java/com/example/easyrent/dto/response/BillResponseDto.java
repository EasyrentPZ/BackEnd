package com.example.easyrent.dto.response;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class BillResponseDto
{
    private Integer paymentId;
    private LocalDate issueDate;
    private MultipartFile pdfFile;
}
