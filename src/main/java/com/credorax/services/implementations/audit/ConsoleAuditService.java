package com.credorax.services.implementations.audit;

import com.credorax.exceptions.CredoraxException;
import com.credorax.models.dao.AuditEvent;
import com.credorax.services.interfaces.AuditService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

@Service("consoleAuditService")
public class ConsoleAuditService implements AuditService {

    private final OutputStream outputStream = System.out;

    @Override
    public void auditEvent(AuditEvent event) {
        try {
            outputStream.write(event.toString().getBytes());
            outputStream.flush();
        } catch (IOException e) {
        }
    }

}
