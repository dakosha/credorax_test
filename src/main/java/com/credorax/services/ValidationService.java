package com.credorax.services;

import com.credorax.models.dto.PaymentDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class ValidationService {

    private final String creditCardRegex = "^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|" +
            "(?<mastercard>5[1-5][0-9]{14})|" +
            "(?<discover>6(?:011|5[0-9]{2})[0-9]{12})|" +
            "(?<amex>3[47][0-9]{13})|" +
            "(?<diners>3(?:0[0-5]|[68][0-9])?[0-9]{11})|" +
            "(?<jcb>(?:2131|1800|35[0-9]{3})[0-9]{11}))$";
    private final Pattern creditCardPattern = Pattern.compile(creditCardRegex);
    private final String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private final Pattern emailPattern = Pattern.compile(emailRegex);

    public Optional<Map<String, String>> validatePaymentRequest(PaymentDTO paymentRequest) {
        Map<String, String> errors = new LinkedHashMap<>();
        Boolean hasErrors = false;

        //Payment Amount
        if (paymentRequest.getAmount() == null) {
            hasErrors = true;
            errors.put("amount", "Amount should be provided");
        }
        if (paymentRequest.getAmount() != null && paymentRequest.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            hasErrors = true;
            errors.put("amount", "Amount should be a positive value");
        }


        //Payment Currency
        if (paymentRequest.getCurrency() == null) {
            hasErrors = true;
            errors.put("currency", "Currency should be provided");
        }
        if (paymentRequest.getCurrency() != null && paymentRequest.getCurrency().length() <= 0) {
            hasErrors = true;
            errors.put("currency", "Currency should be a non-empty value");
        }

        //Payment Invoice
        if (paymentRequest.getInvoice() == null) {
            hasErrors = true;
            errors.put("invoice", "Invoice should be provided");
        }
        if (paymentRequest.getInvoice() != null && paymentRequest.getInvoice().length() <= 0) {
            hasErrors = true;
            errors.put("invoice", "Invoice should be a non-empty value");
        }

        //Payment Card
        if (paymentRequest.getCard() == null) {
            hasErrors = true;
            errors.put("card", "Card should be provided");
        }
        if (paymentRequest.getCard() != null) {
            //Card CVV
            if (paymentRequest.getCard().getCvv() == null) {
                hasErrors = true;
                errors.put("card", "CVV should be provided");
            }
            if (paymentRequest.getCard().getCvv() != null && paymentRequest.getCard().getCvv().length() <= 0) {
                hasErrors = true;
                errors.put("card", "CVV should be a non-empty value");
            }
            //Card number
            if (paymentRequest.getCard().getPan() == null) {
                hasErrors = true;
                errors.put("card", "Card number should be provided");
            }
            if (paymentRequest.getCard().getPan() != null && paymentRequest.getCard().getPan().length() <= 0) {
                hasErrors = true;
                errors.put("card", "Card number should be a non-empty value");
            }
            if (paymentRequest.getCard().getPan() != null && !validateCardNumber(paymentRequest.getCard().getPan())) {
                hasErrors = true;
                errors.put("card", "Card number should be a valid number (visa, mastercard, american express, etc...");
            }
            //Card Expiry
            if (paymentRequest.getCard().getExpiry() == null) {
                hasErrors = true;
                errors.put("card", "Expiry date should be provided");
            }
            if (paymentRequest.getCard().getExpiry() != null && paymentRequest.getCard().getExpiry().length() != 4) {
                hasErrors = true;
                errors.put("card", "Expiry date should be a 4 digits value (example: '0425', April 2025)");
            }
            if (paymentRequest.getCard().getExpiry() != null && paymentRequest.getCard().getExpiry().length() == 4 && !validateDate(paymentRequest.getCard().getExpiry())) {
                hasErrors = true;
                errors.put("card", "The card is Expired");
            }
        }

        //Payment Card Holder
        if (paymentRequest.getCardHolder() == null) {
            hasErrors = true;
            errors.put("cardHolder", "Card Holder should be provided");
        }
        if (paymentRequest.getCardHolder() != null) {
            //Card Holder
            if (paymentRequest.getCardHolder().getEmail() == null) {
                hasErrors = true;
                errors.put("cardHolder", "Email should be provided");
            }
            if (paymentRequest.getCardHolder().getEmail() != null && paymentRequest.getCardHolder().getEmail().length() <= 0) {
                hasErrors = true;
                errors.put("cardHolder", "Email should be a non-empty value");
            }
            if (paymentRequest.getCardHolder().getEmail() != null && !validateEmail(paymentRequest.getCardHolder().getEmail())) {
                hasErrors = true;
                errors.put("cardHolder", "Email should be a valid Email address");
            }

            //Card Holder
            if (paymentRequest.getCardHolder().getName() == null) {
                hasErrors = true;
                errors.put("cardHolder", "Name should be provided");
            }
            if (paymentRequest.getCardHolder().getName() != null && paymentRequest.getCardHolder().getName().length() <= 0) {
                hasErrors = true;
                errors.put("cardHolder", "Name should be a non-empty value");
            }
        }

        return hasErrors ? Optional.of(errors) : Optional.empty();
    }

    private boolean validateDate(String expiry) {
        LocalDate cardDate = LocalDate.parse("01" + expiry, DateTimeFormatter.ofPattern("ddMMyy"));
        cardDate = cardDate.withDayOfMonth(cardDate.getMonth().length(cardDate.isLeapYear()));
        Date currentDate = new Date();

        return LocalDate.now().isBefore(cardDate);
    }

    public Optional<List<?>> validateInvoice(String invoice) {
        return Optional.empty();
    }

    private boolean validateCardNumber(String cardNumber) {
        return creditCardPattern.matcher(cardNumber).matches();
    }

    private boolean validateEmail(String email) {
        return emailPattern.matcher(email).matches();
    }

}
