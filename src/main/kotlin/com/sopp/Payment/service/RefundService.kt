package com.sopp.Payment.service

import com.sopp.Payment.entity.RefundEntity
import com.sopp.Payment.repository.PaymentOrderRepository
import com.sopp.Payment.repository.RefundRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@Service
class RefundService(
    private val refundRepository: RefundRepository,
    private val orderRepository: PaymentOrderRepository,
    private val walletService: WalletService
) {

    fun createRefund(orderId: UUID){
        val order = orderRepository.findById(orderId)
        if (order.isPresent){
            val orderEntity = order.get()
            if(refundRepository.findByOrderId(orderId).isPresent) throw ResponseStatusException(HttpStatus.CONFLICT, "There is a refund request for this payment order. You can not create new one.")
            refundRepository.save(RefundEntity(orderId=orderId, customerId = orderEntity.customerId, merchantId = orderEntity.merchantId ,completed = false))
        }
        else throw ResponseStatusException(HttpStatus.NOT_FOUND, "Payment order not found")
    }

    suspend fun completeRefund(orderId: UUID){
        val refund = refundRepository.findByOrderId(orderId)
        if (refund.isPresent){
            val refundEntity =  refund.get()
            if(!refundEntity.completed){
                val orderEntity = orderRepository.findById(orderId).get()
                try {
                    walletService.addMoney(orderEntity.customerId, orderEntity.paymentAmount)
                    walletService.withdrawMoney(orderEntity.merchantId, orderEntity.paymentAmount)
                    refundEntity.completed=true
                    refundRepository.save(refundEntity)
                }catch (e: Exception){
                    throw e
                }
            }
            else throw ResponseStatusException(
                HttpStatus.CONFLICT, "This refund is already processed"
            )
        }
        else throw ResponseStatusException(
            HttpStatus.NOT_FOUND, "Refund order not found"
        );
    }

    fun getRefundsOfCustomer(customerId: String): List<RefundEntity> {
        return refundRepository.findByCustomerId(customerId)
    }

    fun getRefundsOfMerchant(merchantId: String): List<RefundEntity> {
        return refundRepository.findByMerchantId(merchantId)
    }
}