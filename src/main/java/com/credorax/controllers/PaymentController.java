package com.credorax.controllers;

import com.credorax.models.dao.Payment;
import com.credorax.models.dao.PaymentResponse;
import com.credorax.models.dto.PaymentDTO;
import com.credorax.services.ConverterService;
import com.credorax.services.PaymentService;
import com.credorax.services.ValidationService;
import com.credorax.utils.ThreadLocalVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("payments")
public class PaymentController {

    private final ValidationService validationService;
    private final PaymentService paymentService;
    private final ConverterService converterService;

    @Autowired
    public PaymentController(ValidationService validationService, PaymentService paymentService, ConverterService converterService) {
        this.validationService = validationService;
        this.paymentService = paymentService;
        this.converterService = converterService;
    }

    @PostMapping
    public ResponseEntity<Object> processPayment(@RequestBody PaymentDTO paymentDTO,
                                                 @RequestParam(value = "transactionId", required = false) String transactionId) {
        ThreadLocalVariables.setTransactionId(transactionId == null ? UUID.randomUUID().toString() : transactionId);
        Optional<Map<String, String>> validationErrors = validationService.validatePaymentRequest(paymentDTO);
        if (validationErrors.isEmpty()) {
            Payment payment = converterService.convertPayment(paymentDTO);
            PaymentResponse paymentResponse = paymentService.processPayment(payment);
            if (paymentResponse.getApproved()) {
                return ResponseEntity.ok(paymentResponse);
            } else {
                return ResponseEntity.status(400).body(paymentResponse);
            }
        } else {
            PaymentResponse.PaymentResponseBuilder builder = new PaymentResponse.PaymentResponseBuilder();
            validationErrors.get().forEach((key, value) -> {
                builder.withError(key, value);
            });
            return ResponseEntity
                    .status(400)
                    .body(builder.build());
        }
    }

    @GetMapping
    public ResponseEntity<Object> getPayments(@RequestParam(value = "invoice", required = false) String invoice) {
        Optional<Map<String, String>> validationErrors = validationService.validateInvoice(invoice);
        if (validationErrors.isEmpty()) {
            Optional<Payment> payment = paymentService.getPayment(invoice);
            if (payment.isPresent()) {
                PaymentDTO paymentDTO = converterService.convertPayment(payment.get());
                return ResponseEntity.ok(paymentDTO);
            } else {
                return ResponseEntity
                        .status(404)
                        .body(new PaymentResponse.PaymentResponseBuilder()
                                .withError("invoice", "Invoice number is not found")
                                .build());
            }
        } else {
            PaymentResponse.PaymentResponseBuilder builder = new PaymentResponse.PaymentResponseBuilder();
            validationErrors.get().forEach((key, value) -> {
                builder.withError(key, value);
            });
            return ResponseEntity
                    .status(400)
                    .body(builder.build());
        }
    }

}
