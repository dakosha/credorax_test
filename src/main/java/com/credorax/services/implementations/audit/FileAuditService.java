package com.credorax.services.implementations.audit;

import com.credorax.exceptions.CredoraxException;
import com.credorax.models.dao.AuditEvent;
import com.credorax.services.interfaces.AuditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service("fileAuditService")
public class FileAuditService implements AuditService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileAuditService.class);

    @Value("${credorax.services.auditService.file:./audit/audit_temp.log}")
    private String fileName;
    private FileOutputStream fileOutputStream;

    @PostConstruct
    public void init() {
        try {
            File nf = new File(fileName);
            nf.getParentFile().mkdirs();
            nf.createNewFile();
            fileOutputStream = new FileOutputStream(nf);
        } catch (FileNotFoundException e) {
            LOGGER.error("File not found or cannot be created", e);
            throw new CredoraxException("File not found or cannot be created", e);
        } catch (IOException e) {
            LOGGER.error("File not found or cannot be created", e);
            throw new CredoraxException("File not found or cannot be created", e);
        }
    }

    @Override
    public void auditEvent(AuditEvent event) {
        try {
            fileOutputStream.write((event.toString() + "\n").getBytes());
            fileOutputStream.flush();
        } catch (IOException e) {
        }
    }

}
