package com.credorax.services.implementations;

import com.credorax.models.dao.AuditEvent;
import com.credorax.services.interfaces.AuditService;
import org.springframework.stereotype.Service;

@Service("databaseAuditService")
public class DatabaseAuditService implements AuditService {
    @Override
    public void auditEvent(AuditEvent event) {
        throw new RuntimeException("Not Implemented");
    }
}
