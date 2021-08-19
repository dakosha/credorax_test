package com.credorax.controllers;

import com.credorax.models.dao.Payment;
import com.credorax.models.dao.PaymentResponse;
import com.credorax.models.dto.PaymentDTO;
import com.credorax.models.dto.PaymentResponseDTO;
import com.credorax.services.ConverterService;
import com.credorax.services.PaymentService;
import com.credorax.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("payments")
public class PaymentController {

    @Autowired
    private ValidationService validationService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ConverterService converterService;

    @PostMapping
    public PaymentResponse processPayment(@RequestBody PaymentDTO paymentDTO) {
        Optional<Map<String, String>> validationErrors = validationService.validatePaymentRequest(paymentDTO);
        if (validationErrors.isEmpty()) {
            Payment payment = converterService.convertPayment(paymentDTO);
            return paymentService.processPayment(payment);
        } else {
            PaymentResponse.PaymentResponseBuilder builder = new PaymentResponse.PaymentResponseBuilder();
            validationErrors.get().forEach((key, value) -> {
                builder.withError(key, value);
            });
            return builder.build();
        }
    }

    @GetMapping
    public PaymentDTO getPayments(@RequestParam(value = "invoice", required = true) String invoice) {
        Optional<List<?>> validationErrors = validationService.validateInvoice(invoice);
        if (validationErrors.isEmpty()) {
            Payment payment = paymentService.getPayment(invoice);
            PaymentDTO paymentDTO = converterService.convertPayment(payment);
            return paymentDTO;
        } else {
            //response with errors.
            return null;
        }
    }

}
