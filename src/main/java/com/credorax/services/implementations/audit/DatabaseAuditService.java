package com.credorax.services.implementations.audit;

import com.credorax.models.dao.AuditEvent;
import com.credorax.repositories.AuditRepository;
import com.credorax.services.interfaces.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("databaseAuditService")
public class DatabaseAuditService implements AuditService {

    @Autowired
    private AuditRepository auditRepository;

    @Override
    public void auditEvent(AuditEvent event) {
        event.setId(UUID.randomUUID().toString());
        auditRepository.save(event);
    }

    /**
     * This method is just to show that it is possible to get Audit Events.
     * Of course we will need to filter it by dates and make it as a stream instead of List data structure.
     * And return json-lines in stream.
     *
     * @return
     */
    @Override
    public List<AuditEvent> getAllEvents() {
        Iterable<AuditEvent> eventIterable = auditRepository.findAll();
        List<AuditEvent> result = new ArrayList<>();
        eventIterable.forEach(it -> {
            result.add(it);
        });
        return result;
    }
}
