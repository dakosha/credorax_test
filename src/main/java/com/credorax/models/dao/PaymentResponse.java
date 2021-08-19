package com.credorax.models.dao;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentResponse {

    private Boolean approved;
    private Map<String, String> errors;

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentResponse that = (PaymentResponse) o;
        return approved.equals(that.approved) && Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(approved, errors);
    }

    public static class PaymentResponseBuilder {
        private Boolean approved;
        private Map<String, String> errors;

        public PaymentResponseBuilder approved() {
            this.approved = true;
            return this;
        }

        public PaymentResponseBuilder notApproved() {
            this.approved = false;
            return this;
        }

        public PaymentResponseBuilder withError(String errorName, String errorMessage) {
            if (errors == null) {
                errors = new LinkedHashMap<>();
            }
            errors.put(errorName, errorMessage);
            return this;
        }

        public PaymentResponse build() {
            PaymentResponse response = new PaymentResponse();
            response.setApproved(this.approved);
            response.setErrors(this.errors);
            return response;
        }
    }

}
