package com.credorax.models.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class PaymentDTO {

    private String invoice;
    private BigDecimal amount;
    private String currency;
    private CardHolderDTO cardHolder;
    private CardDTO card;

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

    public CardHolderDTO getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(CardHolderDTO cardHolder) {
        this.cardHolder = cardHolder;
    }

    public CardDTO getCard() {
        return card;
    }

    public void setCard(CardDTO card) {
        this.card = card;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentDTO that = (PaymentDTO) o;
        return invoice.equals(that.invoice) && amount.equals(that.amount) && currency.equals(that.currency) && cardHolder.equals(that.cardHolder) && card.equals(that.card);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoice, amount, currency, cardHolder, card);
    }
}
