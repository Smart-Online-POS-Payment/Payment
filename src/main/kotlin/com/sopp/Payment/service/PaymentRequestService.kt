package com.sopp.Payment.service

import com.sopp.Payment.entity.PaymentTransactionEntity
import com.sopp.Payment.model.PaymentTransactionModel
import com.sopp.Payment.repository.PaymentTransactionRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class PaymentRequestService(
    private var paymentTransactionRepository: PaymentTransactionRepository
    ) {

    suspend fun createPaymentRequest(paymentTransactionModel: PaymentTransactionModel): UUID {
        var paymentTransactionEntity = PaymentTransactionEntity(paymentTransactionModel)
        return paymentTransactionRepository.save(paymentTransactionEntity).id
    }

    suspend fun cancelPaymentRequest(uuid: UUID){
        paymentTransactionRepository.deleteById(uuid)
    }

    suspend fun cancelPaymentRequests(){
        paymentTransactionRepository.deleteAll()
    }

    suspend fun getPaymentRequestDetail(uuid:UUID): PaymentTransactionEntity {
        return paymentTransactionRepository.findById(uuid).get()
    }
}