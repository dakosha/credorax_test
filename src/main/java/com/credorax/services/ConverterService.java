package com.credorax.services;

import com.credorax.models.dao.Payment;
import com.credorax.models.dto.CardDTO;
import com.credorax.models.dto.CardHolderDTO;
import com.credorax.models.dto.PaymentDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConverterService {

    private final ObjectMapper objectMapper;

    @Autowired
    public ConverterService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Payment convertPayment(PaymentDTO paymentDTO) {
        Payment payment = new Payment();

        payment.setAmount(paymentDTO.getAmount());
        payment.setCurrency(paymentDTO.getCurrency());
        payment.setCvv(paymentDTO.getCard().getCvv());
        payment.setEmail(paymentDTO.getCardHolder().getEmail());
        payment.setExpiry(paymentDTO.getCard().getExpiry());
        payment.setPan(paymentDTO.getCard().getPan());
        payment.setInvoice(paymentDTO.getInvoice());
        payment.setName(paymentDTO.getCardHolder().getName());

        return payment;
    }

    public PaymentDTO convertPayment(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO();

        paymentDTO.setAmount(payment.getAmount());
        paymentDTO.setCurrency(payment.getCurrency());
        paymentDTO.setInvoice(payment.getInvoice());

        paymentDTO.setCard(convertCard(payment));
        paymentDTO.setCardHolder(convertCardHolder(payment));

        return paymentDTO;
    }

    private CardHolderDTO convertCardHolder(Payment payment) {
        CardHolderDTO result = new CardHolderDTO();
        result.setEmail(payment.getEmail());
        result.setName(payment.getName());
        return result;
    }

    private CardDTO convertCard(Payment payment) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setExpiry(payment.getExpiry());
        cardDTO.setPan(payment.getPan());
        cardDTO.setCvv(payment.getCvv());
        return cardDTO;
    }

    public <S, T> T convert(S object, Class<T> clazz) {
        try {
            String tempObject = objectMapper.writeValueAsString(object);
            T response = objectMapper.readValue(tempObject, clazz);
            return response;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("error", e);
        }
    }

}
