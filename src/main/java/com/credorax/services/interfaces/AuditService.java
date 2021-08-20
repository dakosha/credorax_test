package com.credorax.services.interfaces;

import com.credorax.models.dao.AuditEvent;

import java.util.Collections;
import java.util.List;

public interface AuditService {

    void auditEvent(AuditEvent event);

    default List<AuditEvent> getAllEvents() {
        return Collections.emptyList();
    }

}
