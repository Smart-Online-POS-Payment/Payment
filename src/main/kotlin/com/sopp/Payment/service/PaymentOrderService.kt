package com.sopp.Payment.service

import com.sopp.Payment.entity.PaymentTransactionEntity
import com.sopp.Payment.model.PaymentModel
import com.sopp.Payment.model.PaymentTransactionModel.Type
import com.sopp.Payment.model.ResponseModel
import com.sopp.Payment.repository.PaymentTransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import java.util.*

@Service
class PaymentOrderService(
    private val paymentTransactionRepository: PaymentTransactionRepository,
    private val walletService: WalletService,
) {
    suspend fun createPaymentOrder(
        uuid: UUID,
        customerId: String,
    ): ResponseModel {
        val paymentRequest = paymentTransactionRepository.findById(uuid).get()
        return if (paymentRequest.type == Type.RequestSale) {
            val withdrawResponse = walletService.withdrawMoney(customerId, paymentRequest.paymentAmount)
            if (withdrawResponse.statusCode == "200") {
                walletService.addMoney(paymentRequest.merchantId, paymentRequest.paymentAmount) // ToDo: Retry until successfull
                paymentRequest.type = Type.FinalizeSale
                paymentRequest.customerId = customerId
                val payment = paymentTransactionRepository.save(paymentRequest)
                // kafkaProducer.sendStringMessage(payment)
                ResponseModel("200", "Successfull payment")
            } else {
                ResponseModel("500", "Internal error")
            }
        } else {
            ResponseModel("400", "This order is already processed")
        }
    }

    suspend fun getPaymentsOfUser(customerId: String): List<PaymentModel> {
        val payments =
            withContext(Dispatchers.IO) {
                paymentTransactionRepository.findFinalizeSalePaymentsByCustomerId(customerId)
            }

        return payments
            .map { PaymentModel(it) }
    }

    suspend fun getPaymentsOfMerchant(merchantId: String): List<PaymentModel> {
        val payments =
            withContext(Dispatchers.IO) {
                paymentTransactionRepository.findFinalizeSalePaymentsByMerchantId(merchantId)
            }
        return payments.map {
            PaymentModel(it)
        }
    }

    suspend fun getPaymentTransaction(merchantId: String): List<PaymentTransactionEntity> {
        return withContext(Dispatchers.IO) {
            paymentTransactionRepository.findByMerchantId(merchantId)
        }
    }
}
