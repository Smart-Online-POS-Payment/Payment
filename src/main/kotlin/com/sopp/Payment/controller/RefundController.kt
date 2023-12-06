package com.sopp.Payment.controller

import com.sopp.Payment.entity.RefundEntity
import com.sopp.Payment.service.RefundService
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("payment/refund")
@CrossOrigin(origins = ["http://localhost:3000"])
class RefundController(
    private val refundService: RefundService
) {

    @PostMapping("/{orderId}")
    fun createRefund(@PathVariable orderId: UUID){
        refundService.createRefund(orderId)
    }

    @PutMapping("/{orderId}")
    suspend fun completeRefund(@PathVariable orderId: UUID){
        refundService.completeRefund(orderId)
    }

    @GetMapping("/customer/{customerId}")
    fun getCustomerRefunds(@PathVariable customerId: String): List<RefundEntity> {
        return refundService.getRefundsOfCustomer(customerId)
    }

    @GetMapping("/merchant/{merchantId}")
    fun getMerchantRefunds(@PathVariable merchantId: String): List<RefundEntity> {
        return refundService.getRefundsOfMerchant(merchantId)
    }
}