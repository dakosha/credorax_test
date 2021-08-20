# credorax test Assignment

### This Application is the test assignment for the Credorax.

#### Terminal commands:

1. To run the application: you need to type in terminal next command: 
   1. ./gradlew bootRun
2. To run tests: './gradlew check' - this will either run tests and generate Java Code Coverage report (will be available: './build/jacocoHtml/index.html')

#### Configuration:

1. You can configure which Encryption way to use by the configuraion: 'credorax.services.encryptionService.name'.
   1. Possible values are: ['base64EncryptionService','defaultEncryptionService']
   2. base64EncryptionService will use simple Base64 algorithm to encode the data.
   3. defaultEncryptionService will leave the data as it is. Without any encryption.
2. You can configure which Audit service to use: 'credorax.services.auditService.name'
   1. Possible values are: ['consoleAuditService','fileAuditService','databaseAuditService']
      1. consoleAuditService will use console output to log all audit event.
      2. fileAuditService will use, by default './audit/audit_temp.log', file to store all audit events.
         1. you can configure which file to use: 'credorax.services.auditService.file'
      3. databaseAuditService will use database to store all audit events.

### How to use API:
1. #### POST http://localhost:8080/payments with JSON body of the payment will create payment and audit the log with appropriate service.
   1. example: curl --request POST \
      --url http://localhost:8080/payments \
      --header 'Content-Type: application/json' \
      --data '{
      "invoice": "124",
      "amount": 114,
      "currency": "EUR",
      "cardHolder": {
      "name": "Dauren Mussa",
      "email": "dauren.mussa@gmail.com"
      },
      "card": {
      "pan": "5437590060003409",
      "expiry": "0822",
      "cvv": "789"
      }
      }'
2. #### GET http://localhost:8080/payments?invoice=124 will return the Payment from the Database. (masked data)
   1. example: curl --request GET \
      --url 'http://localhost:8080/payments?invoice=124' \
      --header 'x-api-key: cd65d44f7497435fa63fd116c1d1b418'
3. #### GET http://localhost:8080/audit-events will return all audit events stored in Databse (only if Database Audit Service Configured, for other types it will return an empty list.)
   1. example: curl --request GET \
      --url http://localhost:8080/audit-events
      