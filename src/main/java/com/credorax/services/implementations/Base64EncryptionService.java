package com.credorax.services.implementations;

import com.credorax.models.dao.Payment;
import com.credorax.services.interfaces.EncryptionService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service("base64EncryptionService")
public class Base64EncryptionService implements EncryptionService {

    @Override
    public Payment encrypt(Payment payment) {
        Payment result = new Payment();

        result.setId(payment.getId());
        result.setName(encryptWithBase64(payment.getName()));
        result.setInvoice(payment.getInvoice());
        result.setPan(encryptWithBase64(payment.getPan()));
        result.setExpiry(encryptWithBase64(payment.getExpiry()));
        result.setEmail(payment.getEmail());
        result.setCvv(payment.getCvv());
        result.setCurrency(payment.getCurrency());
        result.setAmount(payment.getAmount());

        return result;
    }

    private String encryptWithBase64(String source) {
        String encoded = new String(Base64.getEncoder().encode(source.getBytes()));
        return encoded;
    }

    private String decryptWithBase64(String source) {
        String decoded = new String(Base64.getDecoder().decode(source.getBytes()));
        return decoded;
    }

    @Override
    public Payment decrypt(Payment payment) {
        Payment result = new Payment();

        result.setId(payment.getId());
        result.setName(decryptWithBase64(payment.getName()));
        result.setInvoice(payment.getInvoice());
        result.setPan(decryptWithBase64(payment.getPan()));
        result.setExpiry(decryptWithBase64(payment.getExpiry()));
        result.setEmail(payment.getEmail());
        result.setCvv(payment.getCvv());
        result.setCurrency(payment.getCurrency());
        result.setAmount(payment.getAmount());

        return maskData(result);
    }

    private Payment maskData(Payment payment) {
        Payment result = new Payment();

        result.setId(payment.getId());
        result.setName(maskString(payment.getName()));
        result.setInvoice(payment.getInvoice());
        result.setPan(maskPan(payment.getPan()));
        result.setExpiry(maskString(payment.getExpiry()));
        result.setEmail(payment.getEmail());
        result.setCvv(payment.getCvv());
        result.setCurrency(payment.getCurrency());
        result.setAmount(payment.getAmount());

        return result;
    }

    private String maskString(String source) {
        StringBuilder st = new StringBuilder();
        for (int i = 0; i < source.length(); i++) {
            st.append("*");
        }
        return st.toString();
    }

    private String maskPan(String source) {
        StringBuilder st = new StringBuilder();
        for (int i = 0; i < source.length() - 4; i++) {
            st.append("*");
        }
        for (int i = source.length() - 4; i < source.length(); i++) {
            st.append(source.charAt(i));
        }
        return st.toString();
    }

}
