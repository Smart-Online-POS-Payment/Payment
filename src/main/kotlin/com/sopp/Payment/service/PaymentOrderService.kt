package com.sopp.Payment.service

import com.sopp.Payment.config.producer.KafkaProducer
import com.sopp.Payment.entity.PaymentOrderEntity
import com.sopp.Payment.model.PaymentModel
import com.sopp.Payment.model.ResponseModel
import com.sopp.Payment.model.TransactionType
import com.sopp.Payment.repository.PaymentOrderRepository
import com.sopp.Payment.repository.PaymentRequestRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PaymentOrderService(
    private val paymentOrderRepository: PaymentOrderRepository,
    private val paymentRequestRepository: PaymentRequestRepository,
    private val walletService: WalletService,
    private val kafkaProducer: KafkaProducer
) {
    suspend fun createPaymentOrder(uuid: UUID, customerId: String): ResponseModel {
        if (paymentOrderRepository.existsById(uuid)){
            return ResponseModel("400", "This order is already processed")
        }
        val paymentRequest = paymentRequestRepository.findById(uuid).get()
        val withdrawResponse = walletService.withdrawMoney(customerId, paymentRequest.paymentAmount)
        print(withdrawResponse)
        if(withdrawResponse.statusCode=="200"){
            walletService.addMoney(paymentRequest.merchantId, paymentRequest.paymentAmount)// ToDo: Retry until successfull
            val paymentOrder = paymentOrderRepository.save(PaymentOrderEntity(id = paymentRequest.id, merchantId = paymentRequest.merchantId, customerId = customerId, paymentAmount = paymentRequest.paymentAmount, paymentMessage = paymentRequest.paymentMessage, paymentDate = null))
            kafkaProducer.sendStringMessage(paymentOrder)
            return ResponseModel("200", "Successfull payment")
            return withdrawResponse
        }
        else{
            return ResponseModel("500", "Internal error")
        }
    }

    suspend fun getPaymentsOfUser(customerId: String): List<PaymentModel> {
        val  paymentOrderEntities = paymentOrderRepository.findByCustomerId(customerId)
        return paymentOrderEntities.map { PaymentModel(it) }
    }

    suspend fun getPaymentsOfMerchant(merchantId: String): List<PaymentOrderEntity> {
        return paymentOrderRepository.findByMerchantId(merchantId)
    }

}