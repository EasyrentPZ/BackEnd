package com.example.easyrent.repository;

import com.example.easyrent.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, Integer>
{
    PaymentStatus findPaymentStatusByName(String name);
}