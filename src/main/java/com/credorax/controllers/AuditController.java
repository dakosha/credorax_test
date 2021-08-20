package com.credorax.controllers;

import com.credorax.models.dao.AuditEvent;
import com.credorax.services.interfaces.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("audit-events")
public class AuditController {

    @Autowired
    private AuditService auditService;

    @GetMapping
    public List<AuditEvent> getEvents() {
        return auditService.getAllEvents();
    }

}
