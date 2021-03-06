package com.credorax.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardDTO {

    private String pan;
    private String expiry;
    private String cvv;

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

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardDTO card = (CardDTO) o;
        return pan.equals(card.pan) && expiry.equals(card.expiry) && cvv.equals(card.cvv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pan, expiry, cvv);
    }
}
