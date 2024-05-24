package com.example.easyrent.service;

import com.example.easyrent.dto.request.PaymentAddDto;
import com.example.easyrent.dto.response.BillsResponseDto;
import com.example.easyrent.dto.response.PaymentInfoDto;
import com.example.easyrent.dto.response.PaymentsInfoDto;
import com.example.easyrent.model.*;
import com.example.easyrent.repository.PaymentRepository;
import com.example.easyrent.repository.PaymentStatusRepository;
import com.example.easyrent.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final UserService userService;
    private final PropertyRepository propertyRepository;
    private final PaymentStatusRepository paymentStatusRepository;
    private final PaymentRepository paymentRepository;

    public void addPayment(String token, Integer propertyId, PaymentAddDto request) throws Exception
    {
        try
        {
            User owner = userService.getUserFromToken(token);
            Property property = propertyRepository.findPropertyById(propertyId);
            PaymentStatus status = paymentStatusRepository.findPaymentStatusByName("Pending");

            Payment payment = new Payment();
            payment.setPaymentStatus(status);
            payment.setAmount(request.getAmount());
            payment.setContract(property.getContract());
            payment.setTitle(request.getTitle());
            payment.setInvoiceNumber(request.getInvoiceNumber());
            Date issueDate = request.getIssueDate();
            Instant instant = issueDate.toInstant();
            LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            payment.setIssueDate(localDate);

            byte[] pdfBytes = request.getInvoice().getBytes();
            payment.setBillPdf(pdfBytes);
            paymentRepository.save(payment);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error!");
        }
    }

    public PaymentsInfoDto getPayments(String token, Integer propertyId) throws Exception
    {
        User owner = userService.getUserFromToken(token);
        Property property = propertyRepository.findPropertyById(propertyId);
        PaymentsInfoDto response = new PaymentsInfoDto();
        List<PaymentInfoDto> responses = new LinkedList();
        for(Payment payment: property.getContract().getPayments())
        {
            PaymentInfoDto x = new PaymentInfoDto();
            x.setPaymentStatus(payment.getPaymentStatus().getName());
            x.setIssueDate(payment.getIssueDate());
            x.setPaymentId(payment.getId());
            responses.add(x);
        }
        response.setPayments(responses);
        return response;
    }

    public ResponseEntity<byte[]> getPdf(String token, Integer paymentId) throws Exception
    {
        Payment payment = paymentRepository.findPaymentById(paymentId);
        byte[] pdf = payment.getBillPdf();
        File file = new File("C:\\test\\test.pdf");
        FileUtils.writeByteArrayToFile(file, pdf);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_PDF);
        header.setContentLength(pdf.length);
        header.set("Content-Disposition", "attachment; filename=" + "pdf-" + ".pdf");
        return new ResponseEntity<>(pdf, header, HttpStatus.OK);
    }
}
