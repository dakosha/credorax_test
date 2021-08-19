package com.credorax.services.implementations;

import com.credorax.models.dao.Payment;
import com.credorax.services.interfaces.EncryptionService;
import org.springframework.stereotype.Service;

@Service("defaultEncryptionService")
public class DefaultEncryptionService implements EncryptionService {
    @Override
    public Payment encrypt(Payment payment) {
        return payment;
    }

    @Override
    public Payment decrypt(Payment payment) {
        return payment;
    }
}
