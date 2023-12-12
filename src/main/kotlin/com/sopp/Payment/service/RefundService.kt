package com.sopp.Payment.service

import com.sopp.Payment.entity.PaymentTransactionEntity
import com.sopp.Payment.model.PaymentTransactionModel.Type
import com.sopp.Payment.model.ResponseModel
import com.sopp.Payment.repository.PaymentTransactionRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@Service
class RefundService(

    private val paymentTransactionRepository: PaymentTransactionRepository,
    private val walletService: WalletService
) {

    fun createRefund(orderId: UUID): ResponseModel {
        val payment = paymentTransactionRepository.findById(orderId)
        if (payment.isPresent){
            val paymentEntity = payment.get()
            when(paymentEntity.type){
                Type.FinalizeSale->{
                    if(paymentTransactionRepository.findByReference(orderId).isPresent) throw ResponseStatusException(HttpStatus.CONFLICT, "There is a refund request for this payment order. You can not create new one.")
                    paymentTransactionRepository.save(PaymentTransactionEntity(paymentEntity, orderId))
                    return ResponseModel("200", "Refund request is created")
                }
                Type.RequestSale -> {
                    return ResponseModel("400", "This payment is not finalized yet")
                }

                Type.RequestRefund -> {
                    return ResponseModel("400", "There is already a refund request for this payment")
                }

                Type.FinalizeRefund -> {
                    return ResponseModel("400", "This payment is already refunded")
                }
            }
        }
        else throw ResponseStatusException(HttpStatus.NOT_FOUND, "Payment order not found")
    }

    suspend fun completeRefund(id: UUID): ResponseModel {
        val payment = paymentTransactionRepository.findById(id)

        if (payment.isPresent){
            val paymentEntity = payment.get()
            when(paymentEntity.type){
                Type.FinalizeSale->{
                    return ResponseModel("400", "There is no refund request for this payment")
                }
                Type.RequestSale -> {
                    return ResponseModel("400", "This payment is not finalized yet")
                }

                Type.RequestRefund -> {
                    try {
                        walletService.addMoney(paymentEntity.customerId!!, paymentEntity.paymentAmount)
                        walletService.withdrawMoney(paymentEntity.merchantId, paymentEntity.paymentAmount)
                        paymentTransactionRepository.deleteById(paymentEntity.reference!!)
                        paymentEntity.type = Type.FinalizeRefund
                        paymentEntity.reference=null
                        paymentTransactionRepository.save(paymentEntity)
                        return ResponseModel("200", "Refund is finalized.")
                    }catch (e: Exception){
                        throw e
                    }
                }

                Type.FinalizeRefund -> {
                    return ResponseModel("400", "This payment is already refunded")
                }
            }
        }else throw ResponseStatusException(
            HttpStatus.NOT_FOUND, "Refund order not found"
        );
    }

    fun getCustomerRefundRequests(customerId: String): List<PaymentTransactionEntity> {
        return paymentTransactionRepository.findByCustomerId(customerId).filter { it.type == Type.RequestRefund }
    }

    fun getMerchantRefundRequests(merchantId: String): List<PaymentTransactionEntity> {
        return paymentTransactionRepository.findByMerchantId(merchantId).filter { it.type == Type.RequestRefund }
    }

    fun getCustomerRefunds(customerId: String): List<PaymentTransactionEntity>{
        return paymentTransactionRepository.findByCustomerId(customerId).filter { it.type == Type.FinalizeRefund }
    }

    fun getMerchantRefunds(merchantId: String): List<PaymentTransactionEntity>{
        return paymentTransactionRepository.findByMerchantId(merchantId).filter { it.type == Type.FinalizeRefund }
    }
}