package com.credorax.repositories;

import com.credorax.models.dao.AuditEvent;
import org.springframework.data.repository.CrudRepository;

public interface AuditRepository extends CrudRepository<AuditEvent, String> {
}
