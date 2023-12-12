package com.sopp.Payment.service

import com.sopp.Payment.config.producer.KafkaProducer
import com.sopp.Payment.entity.PaymentTransactionEntity
import com.sopp.Payment.model.PaymentModel
import com.sopp.Payment.model.PaymentTransactionModel.Type
import com.sopp.Payment.model.ResponseModel
import com.sopp.Payment.repository.PaymentTransactionRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PaymentOrderService(
    private val paymentTransactionRepository: PaymentTransactionRepository,
    private val walletService: WalletService,
    private val kafkaProducer: KafkaProducer
) {
    suspend fun createPaymentOrder(uuid: UUID, customerId: String): ResponseModel {
        val paymentRequest = paymentTransactionRepository.findById(uuid).get()
        return if(paymentRequest.type == Type.RequestSale){
            val withdrawResponse = walletService.withdrawMoney(customerId, paymentRequest.paymentAmount)
            if(withdrawResponse.statusCode=="200"){
                walletService.addMoney(paymentRequest.merchantId, paymentRequest.paymentAmount)// ToDo: Retry until successfull
                paymentRequest.type = Type.FinalizeSale
                paymentRequest.customerId = customerId
                val payment = paymentTransactionRepository.save(paymentRequest)
                kafkaProducer.sendStringMessage(payment)
                ResponseModel("200", "Successfull payment")
            } else{
                ResponseModel("500", "Internal error")
            }
        } else{
            ResponseModel("400", "This order is already processed")
        }

    }

    suspend fun getPaymentsOfUser(customerId: String): List<PaymentModel> {
        val payments = paymentTransactionRepository.findByCustomerId(customerId)

        return payments
            .filter { it.type == Type.FinalizeSale }
            .map { PaymentModel(it) }
    }

    suspend fun getPaymentsOfMerchant(merchantId: String): List<PaymentTransactionEntity> {
        val payments = paymentTransactionRepository.findByMerchantId(merchantId)
        return payments
            .filter { it.type == Type.FinalizeSale }
    }

}