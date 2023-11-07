package com.sopp.Payment.service

import com.sopp.Payment.entity.PaymentRequestEntity
import com.sopp.Payment.repository.PaymentRequestRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import java.util.*

@Service
class PaymentRequestService(
    private val paymentRequestRepository: PaymentRequestRepository
) {

    suspend fun createPaymentRequest(@RequestBody paymentRequestEntity: PaymentRequestEntity): UUID {
        val paymentRequestEntity=paymentRequestRepository.save(paymentRequestEntity)
        return paymentRequestEntity.id
    }
}