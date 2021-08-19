package com.credorax.repositories;

import com.credorax.models.dao.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, String> {

    Payment findPaymentByInvoice(String invoice);
    Boolean existsByInvoice(String invoice);

}
