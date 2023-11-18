package com.sopp.Payment.service

import com.sopp.Payment.entity.PaymentRequestEntity
import com.sopp.Payment.repository.PaymentRequestRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import java.util.*

@Service
class PaymentRequestService(
    private val paymentRequestRepository: PaymentRequestRepository,
    private val walletService: WalletService
) {

    suspend fun createPaymentRequest(@RequestBody paymentRequestEntity: PaymentRequestEntity): UUID {
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