package com.credorax.services.interfaces;

import com.credorax.models.dao.Payment;

public interface EncryptionService {

    Payment encrypt(Payment payment);

    Payment decrypt(Payment payment);

}
