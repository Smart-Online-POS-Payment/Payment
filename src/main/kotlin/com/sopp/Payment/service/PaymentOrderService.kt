package com.sopp.Payment.service

import com.sopp.Payment.entity.PaymentOrderEntity
import com.sopp.Payment.model.ResponseModel
import com.sopp.Payment.repository.PaymentOrderRepository
import com.sopp.Payment.repository.PaymentRequestRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PaymentOrderService(
    private val paymentOrderRepository: PaymentOrderRepository,
    private val paymentRequestRepository: PaymentRequestRepository,
    private val walletService: WalletService
) {
    suspend fun createPaymentOrder(uuid: UUID, customerId: String): ResponseModel {
        if (paymentOrderRepository.existsById(uuid)){
            return ResponseModel("400", "This order is already processed")
        }
        val paymentRequest = paymentRequestRepository.findById(uuid).get()
        val withdrawResponse = walletService.withdrawMoney(customerId, paymentRequest.paymentAmount)
        if(withdrawResponse.statusCode!="200"){
            return withdrawResponse
        }
        paymentOrderRepository.save(PaymentOrderEntity(id= paymentRequest.id, merchantId = paymentRequest.merchantId, customerId = customerId, paymentAmount = paymentRequest.paymentAmount, paymentMessage = paymentRequest.paymentMessage, paymentDate = null))
        return ResponseModel("200", "Successfull payment")
    }

    suspend fun getPaymentsOfUser(customerId: String): List<PaymentOrderEntity> {
        return paymentOrderRepository.findByCustomerId(customerId)
    }

    suspend fun getPaymentsOfMerchant(merchantId: String): List<PaymentOrderEntity> {
        return paymentOrderRepository.findByMerchantId(merchantId)
    }

}