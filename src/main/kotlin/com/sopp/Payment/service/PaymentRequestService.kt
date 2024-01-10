package com.sopp.Payment.service

import com.sopp.Payment.entity.PaymentTransactionEntity
import com.sopp.Payment.model.PaymentRequestModel
import com.sopp.Payment.repository.PaymentTransactionRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class PaymentRequestService(
    private var paymentTransactionRepository: PaymentTransactionRepository,
) {
    suspend fun createPaymentRequest(paymentRequestModel: PaymentRequestModel): UUID {
        var paymentTransactionEntity = PaymentTransactionEntity(paymentRequestModel)
        return paymentTransactionRepository.save(paymentTransactionEntity).id
    }

    suspend fun cancelPaymentRequest(uuid: UUID) {
        paymentTransactionRepository.deleteById(uuid)
    }

    suspend fun cancelPaymentRequests() {
        paymentTransactionRepository.deleteAll()
    }

    suspend fun getPaymentRequestDetail(uuid: UUID): PaymentTransactionEntity {
        return paymentTransactionRepository.findById(uuid).get()
    }
}
