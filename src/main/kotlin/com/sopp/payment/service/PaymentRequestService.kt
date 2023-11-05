package com.sopp.payment.service

import com.sopp.payment.entity.PaymentRequestEntity
import com.sopp.payment.repository.PaymentRequestRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody

@Service
class PaymentRequestService(
    private val paymentRequestRepository: PaymentRequestRepository
) {

    suspend fun createPaymentRequest(@RequestBody paymentRequestEntity: PaymentRequestEntity) {
        paymentRequestRepository.save(paymentRequestEntity)
    }
}