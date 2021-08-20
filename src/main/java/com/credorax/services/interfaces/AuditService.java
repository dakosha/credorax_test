package com.credorax.services.interfaces;

import com.credorax.models.dao.AuditEvent;

public interface AuditService {

    void auditEvent(AuditEvent event);

}
