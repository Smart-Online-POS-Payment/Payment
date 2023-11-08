package com.sopp.Payment.service

import com.sopp.Payment.entity.PaymentOrderEntity
import com.sopp.Payment.repository.PaymentOrderRepository
import com.sopp.Payment.repository.PaymentRequestRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PaymentOrderService(
    private val paymentOrderRepository: PaymentOrderRepository,
    private val paymentRequestRepository: PaymentRequestRepository
) {
    suspend fun createPaymentOrder(uuid: UUID, customerId: UUID): Boolean {
        val paymentRequest = paymentRequestRepository.findById(uuid).get()
        val paymentOrderEntity:PaymentOrderEntity = paymentOrderRepository.save(PaymentOrderEntity(merchantId = paymentRequest.merchantId, customerId = customerId, paymentAmount = paymentRequest.paymentAmount, paymentMessage = paymentRequest.paymentMessage, paymentDate = null))
        return paymentOrderEntity!=null
    }

    suspend fun getPaymentsOfUser(customerId: UUID): List<PaymentOrderEntity> {
        return paymentOrderRepository.findByCustomerId(customerId)
    }

    suspend fun getPaymentsOfMerchant(merchantId: UUID): List<PaymentOrderEntity> {
        return paymentOrderRepository.findByMerchantId(merchantId)
    }

}