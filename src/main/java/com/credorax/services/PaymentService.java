package com.credorax.services;

import com.credorax.exceptions.CredoraxException;
import com.credorax.models.dao.AuditEvent;
import com.credorax.models.dao.Payment;
import com.credorax.models.dao.PaymentResponse;
import com.credorax.repositories.PaymentRepository;
import com.credorax.services.interfaces.AuditService;
import com.credorax.services.interfaces.EncryptionService;
import com.credorax.utils.ThreadLocalVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final EncryptionService encryptionService;
    private final AuditService auditService;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository,
                          EncryptionService encryptionService,
                          AuditService auditService) {
        this.paymentRepository = paymentRepository;
        this.encryptionService = encryptionService;
        this.auditService = auditService;
    }

    public PaymentResponse processPayment(Payment payment) {
        try {
            if (!paymentRepository.existsByInvoice(payment.getInvoice())) {
                payment.setId(UUID.randomUUID().toString());

                //Encrypting data
                payment = encryptionService.encrypt(payment);

                //Saving data into the Database
                paymentRepository.save(payment);

                //Making an Audit log record. (file based or database based)
                auditService.auditEvent(new AuditEvent.Builder()
                        .approved()
                        .withJsonBody(encryptionService.decrypt(payment).toString())
                        .transactionId(ThreadLocalVariables.getTransactionId())
                        .build());

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
