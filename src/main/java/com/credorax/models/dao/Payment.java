package com.credorax.models.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Objects;

@Entity(name = "payment")
@Table(indexes = {@Index(columnList = "invoice")})
public class Payment {

    @Id
    private String id;

    @Column(unique = true)
    private String invoice;
    private BigDecimal amount;
    private String currency;

    private String name;
    private String email;

    private String pan;
    private String expiry;

    @Transient
    private String cvv;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return invoice.equals(payment.invoice) && amount.equals(payment.amount) && currency.equals(payment.currency) && name.equals(payment.name) && email.equals(payment.email) && pan.equals(payment.pan) && expiry.equals(payment.expiry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoice, amount, currency, name, email, pan, expiry);
    }
}
