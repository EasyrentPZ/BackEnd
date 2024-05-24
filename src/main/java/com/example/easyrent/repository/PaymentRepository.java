package com.example.easyrent.repository;

import com.example.easyrent.model.Payment;
import com.example.easyrent.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer>
{
    Payment findPaymentById(Integer paymentId);
}
