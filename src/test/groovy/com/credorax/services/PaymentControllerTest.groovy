package com.credorax.services

import com.credorax.controllers.PaymentController
import com.credorax.models.dao.AuditEvent
import com.credorax.models.dao.Payment
import com.credorax.models.dto.CardDTO
import com.credorax.models.dto.CardHolderDTO
import com.credorax.models.dto.PaymentDTO
import com.credorax.repositories.PaymentRepository
import com.credorax.services.interfaces.AuditService
import com.credorax.services.interfaces.EncryptionService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

class PaymentControllerTest extends Specification {

    private PaymentRepository paymentRepository = Mock()
    private EncryptionService encryptionService = Mock()
    private AuditService auditService = Mock()
    private PaymentService paymentService = new PaymentService(paymentRepository, encryptionService, auditService)
    private ValidationService validationService = new ValidationService()
    private ConverterService converterService = new ConverterService(new ObjectMapper())
    private PaymentController paymentController = new PaymentController(validationService, paymentService, converterService)

    def paymentOk = new PaymentDTO(
            invoice: '1',
            amount: 100,
            currency: 'EUR',
            cardHolder: new CardHolderDTO(
                    name: 'Alex Petrovich',
                    email: 'alex.p@gmail.com'
            ),
            card: new CardDTO(
                    pan: '4141000055557890',
                    expiry: '0825',
                    cvv: '234'
            ))

    def paymentExpired = new PaymentDTO(
            invoice: '1',
            amount: 100,
            currency: 'EUR',
            cardHolder: new CardHolderDTO(
                    name: 'Alex Petrovich',
                    email: 'alex.p@gmail.com'
            ),
            card: new CardDTO(
                    pan: '4141000055557890',
                    expiry: '0820',
                    cvv: '234'
            ))

    def paymentAmountAbsent = new PaymentDTO(
            invoice: '1',
            currency: 'EUR',
            cardHolder: new CardHolderDTO(
                    name: 'Alex Petrovich',
                    email: 'alex.p@gmail.com'
            ),
            card: new CardDTO(
                    pan: '4141000055557890',
                    expiry: '0825',
                    cvv: '234'
            ))

    def paymentCardHolderWrongEmail = new PaymentDTO(
            invoice: '1',
            amount: 100,
            currency: 'EUR',
            cardHolder: new CardHolderDTO(
                    name: 'Alex Petrovich',
                    email: 'alex.p$gmail.com'
            ),
            card: new CardDTO(
                    pan: '4141000055557890',
                    expiry: '0825',
                    cvv: '234'
            ))

    def processPaymentOK() {
        given:
        def payment = converterService.convertPayment(paymentOk)
        def transactionId = UUID.randomUUID().toString()
        paymentRepository.existsByInvoice('1') >> false
        paymentRepository.findPaymentByInvoice('1') >> payment
        paymentRepository.save(Mock(Payment)) >> payment
        auditService.auditEvent(Mock(AuditEvent)) >> null
        encryptionService.encrypt(payment) >> payment
        encryptionService.decrypt(payment) >> payment

        when:
        ResponseEntity response = paymentController.processPayment(paymentOk, transactionId)

        then:
        response.statusCode == HttpStatus.OK
        response.body.approved == true
    }

    def processPaymentNotOK() {
        given:
        def transactionId = UUID.randomUUID().toString()
        paymentRepository.existsByInvoice('1') >> true
        paymentRepository.findPaymentByInvoice('1') >> null
        paymentRepository.save(paymentOk) >> null

        when:
        ResponseEntity responseAmountAbsent = paymentController.processPayment(paymentAmountAbsent, transactionId)
        ResponseEntity responseWrongEmail = paymentController.processPayment(paymentCardHolderWrongEmail, transactionId)
        ResponseEntity responseExpired = paymentController.processPayment(paymentExpired, transactionId)
        ResponseEntity responseExists = paymentController.processPayment(paymentOk, transactionId)

        then:
        responseAmountAbsent.statusCode == HttpStatus.BAD_REQUEST
        responseAmountAbsent.body.errors.amount == 'Amount should be provided'

        responseWrongEmail.statusCode == HttpStatus.BAD_REQUEST
        responseWrongEmail.body.errors['cardHolder.email'] == 'Email should be a valid Email address'

        responseExpired.statusCode == HttpStatus.BAD_REQUEST
        responseExpired.body.errors['card.expiry'] == 'The card is Expired'

        responseExists.statusCode == HttpStatus.BAD_REQUEST
        responseExists.body.approved == false
        responseExists.body.errors.invoice == 'Invoice number already exists'
    }

    def getPaymentsOK() {
        given:
        def payment = converterService.convertPayment(paymentOk)
        paymentRepository.existsByInvoice('1') >> true
        paymentRepository.findPaymentByInvoice('1') >> payment
        encryptionService.decrypt(payment) >> payment

        when:
        ResponseEntity responseOk = paymentController.getPayments('1')

        then:
        responseOk.statusCode == HttpStatus.OK
        responseOk.body.equals(paymentOk)

    }

}
