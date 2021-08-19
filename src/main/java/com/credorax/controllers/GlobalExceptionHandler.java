package com.credorax.controllers;

import com.credorax.exceptions.PaymentInvoiceAbsenceException;
import com.credorax.models.dao.PaymentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(PaymentInvoiceAbsenceException.class)
    public PaymentResponse handleInvoiceAbsenceException(PaymentInvoiceAbsenceException e) {
        LOGGER.warn("Payment request does not contain invoice as Request Parameter");
        return new PaymentResponse.PaymentResponseBuilder()
                .withError("invoice", "Invoice request parameter is absent")
                .build();
    }

}
