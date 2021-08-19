package com.credorax.services;

import com.credorax.exceptions.CredoraxException;
import com.credorax.models.dao.Payment;
import com.credorax.models.dao.PaymentResponse;
import com.credorax.repositories.PaymentRepository;
import com.credorax.services.interfaces.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private EncryptionService encryptionService;

    public PaymentResponse processPayment(Payment payment) {
        try {
            if (!paymentRepository.existsByInvoice(payment.getInvoice())) {
                payment.setId(UUID.randomUUID().toString());
                payment = encryptionService.encrypt(payment);
                paymentRepository.save(payment);
                return new PaymentResponse.PaymentResponseBuilder()
                        .approved()
                        .build();
            } else {
                return new PaymentResponse.PaymentResponseBuilder()
                        .notApproved()
                        .withError("invoice", "Invoice number already exists")
                        .build();
            }
        } catch (Exception ex) {
            throw new CredoraxException(ex);
        }
    }

    public Optional<Payment> getPayment(String invoice) {
        if (paymentRepository.existsByInvoice(invoice)) {
            Payment payment = paymentRepository.findPaymentByInvoice(invoice);
            payment = encryptionService.decrypt(payment);
            return Optional.of(payment);
        }
        return Optional.empty();
    }
}
