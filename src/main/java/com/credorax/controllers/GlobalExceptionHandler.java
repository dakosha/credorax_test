package com.credorax.controllers;

import com.credorax.exceptions.CredoraxException;
import com.credorax.exceptions.PaymentInvoiceAbsenceException;
import com.credorax.models.dao.PaymentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CredoraxException.class)
    public PaymentResponse handleInvoiceAbsenceException(CredoraxException e) {
        LOGGER.warn(e.getMessage());
        return new PaymentResponse.PaymentResponseBuilder()
                .withError("Internal Error", e.getMessage())
                .build();
    }

}
