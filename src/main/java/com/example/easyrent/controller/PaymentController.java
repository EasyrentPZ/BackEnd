package com.example.easyrent.controller;

import com.example.easyrent.dto.request.PaymentAddDto;
import com.example.easyrent.dto.response.MessageDto;
import com.example.easyrent.dto.response.PaymentInfoDto;
import com.example.easyrent.dto.response.PaymentsInfoDto;
import com.example.easyrent.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController
{
    private final PaymentService paymentService;
    @PostMapping(headers = {"Content-Type=multipart/form-data"}, value = "/add/{propertyId}")
    public ResponseEntity<MessageDto> addBill(@CookieValue("jwtCookie") String jwtToken,
                                                 @PathVariable("propertyId") Integer propertyId,
                                                 @ModelAttribute PaymentAddDto propertyAddRequestDto)
    {
        try
        {
            paymentService.addPayment(jwtToken, propertyId, propertyAddRequestDto);
            return ResponseEntity.ok(new MessageDto("Success"));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageDto("UnknownError"));
        }
    }

    @GetMapping(value = "/{paymentId}", produces = "application/pdf")
    public ResponseEntity<byte[]> getPaymentPdf(@CookieValue("jwtCookie") String jwtToken, @PathVariable("paymentId") Integer paymentId)
    {
        try
        {
            return paymentService.getPdf(jwtToken, paymentId);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/payments/{propertyId}")
    public ResponseEntity<List<PaymentInfoDto>> getAllPayments(@CookieValue("jwtCookie") String jwtToken, @PathVariable("propertyId") Integer propertyId)
    {
        try
        {
            PaymentsInfoDto response = paymentService.getPayments(jwtToken, propertyId);
            return ResponseEntity.ok(response.getPayments());
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
