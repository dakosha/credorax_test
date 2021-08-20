package com.credorax.models.dao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Date;

@Entity(name = "auditEvent")
@Table(indexes = {
        @Index(columnList = "eventDate"),
        @Index(columnList = "approved"),
        @Index(columnList = "transactionId")})
public class AuditEvent {

    @Id
    private String id;
    private Date eventDate;
    private Boolean approved;
    private String jsonBody;
    private String transactionId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public String getJsonBody() {
        return jsonBody;
    }

    public void setJsonBody(String jsonBody) {
        this.jsonBody = jsonBody;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public static class Builder {
        private Date eventDate = new Date();
        private Boolean approved;
        private String jsonBody;
        private String transactionId;

        public Builder approved() {
            this.approved = true;
            return this;
        }

        public Builder notApproved() {
            this.approved = false;
            return this;
        }

        public Builder withJsonBody(String body) {
            this.jsonBody = body;
            return this;
        }

        public Builder transactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public AuditEvent build() {
            AuditEvent event = new AuditEvent();
            event.setEventDate(this.eventDate);
            event.setApproved(this.approved);
            event.setJsonBody(this.jsonBody);
            event.setTransactionId(this.transactionId);
            return event;
        }

    }

    @Override
    public String toString() {
        return "AuditEvent{" +
                "eventDate=" + eventDate +
                ", approved=" + approved +
                ", jsonBody='" + jsonBody + '\'' +
                ", transactionId='" + transactionId + '\'' +
                '}';
    }
}
