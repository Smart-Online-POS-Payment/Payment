package com.sopp.Payment.controller

import com.sopp.Payment.entity.PaymentTransactionEntity
import com.sopp.Payment.model.RefundModel
import com.sopp.Payment.service.RefundService
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("payment/refund")
@CrossOrigin(origins = ["http://localhost:3000"])
class RefundController(
    private val refundService: RefundService,
) {
    @PostMapping("/{orderId}")
    fun createRefund(
        @PathVariable orderId: UUID,
    ) {
        refundService.createRefund(orderId)
    }

    @PutMapping("/{orderId}")
    suspend fun completeRefund(
        @PathVariable orderId: UUID,
    ) {
        refundService.completeRefund(orderId)
    }

    @GetMapping("request/customer/{customerId}")
    fun getCustomerRefundRequests(
        @PathVariable customerId: String,
    ): List<PaymentTransactionEntity> {
        return refundService.getCustomerRefundRequests(customerId)
    }

    @GetMapping("request/merchant/{merchantId}")
    fun getMerchantRefundRequests(
        @PathVariable merchantId: String,
    ): List<RefundModel> {
        return refundService.getMerchantRefundRequests(merchantId)
    }

    @GetMapping("customer/{customerId}")
    fun getCustomerRefunds(
        @PathVariable customerId: String,
    ): List<PaymentTransactionEntity> {
        return refundService.getCustomerRefunds(customerId)
    }

    @GetMapping("merchant/{merchantId}")
    fun getMerchantRefunds(
        @PathVariable merchantId: String,
    ): List<PaymentTransactionEntity> {
        return refundService.getMerchantRefunds(merchantId)
    }
}
