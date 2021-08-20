package com.credorax.configuration;

import com.credorax.services.implementations.audit.FileAuditService;
import com.credorax.services.implementations.encryption.DefaultEncryptionService;
import com.credorax.services.interfaces.AuditService;
import com.credorax.services.interfaces.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public EncryptionService encryptionService(
            @Value("${credorax.services.encryptionService.name}") String encryptionServiceName) {
        try {
            Object bean = applicationContext.getBean(encryptionServiceName);
            return (EncryptionService) bean;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new DefaultEncryptionService();
        }
    }

    @Bean
    public AuditService auditService(
            @Value("${credorax.services.auditService.name}") String encryptionServiceName) {
        try {
            Object bean = applicationContext.getBean(encryptionServiceName);
            return (AuditService) bean;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new FileAuditService();
        }
    }

}
