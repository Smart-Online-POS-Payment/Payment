package com.sopp.Payment.service

import com.sopp.Payment.entity.PaymentRequestEntity
import com.sopp.Payment.repository.PaymentOrderRepository
import com.sopp.Payment.repository.PaymentRequestRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class PaymentRequestService(
    private val paymentRequestRepository: PaymentRequestRepository) {

    suspend fun createPaymentRequest(paymentRequestEntity: PaymentRequestEntity): UUID {
        val paymentRequestEntity=paymentRequestRepository.save(paymentRequestEntity)
        return paymentRequestEntity.id
    }

    suspend fun cancelPaymentRequest(uuid: UUID){
        paymentRequestRepository.deleteById(uuid)
    }

    suspend fun cancelPaymentRequests(){
        paymentRequestRepository.deleteAll()
    }

    suspend fun getPaymentRequestDetail(uuid:UUID): PaymentRequestEntity {
        return paymentRequestRepository.findById(uuid).get()
    }
}