package com.credorax.services;

import com.credorax.models.dao.Payment;
import com.credorax.models.dao.PaymentResponse;
import com.credorax.repositories.PaymentRepository;
import com.credorax.services.interfaces.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private EncryptionService encryptionService;

    public PaymentResponse processPayment(Payment payment) {
        payment.setId(UUID.randomUUID().toString());
        payment = encryptionService.encrypt(payment);
        paymentRepository.save(payment);
        return new PaymentResponse.PaymentResponseBuilder()
                .approved()
                .build();
    }

    public Payment getPayment(String invoice) {
        Payment payment = paymentRepository.findPaymentByInvoice(invoice);
        payment = encryptionService.decrypt(payment);
        return payment;
    }
}
