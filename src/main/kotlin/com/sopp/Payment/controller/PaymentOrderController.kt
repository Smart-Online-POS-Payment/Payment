package com.sopp.Payment.controller

import com.sopp.Payment.entity.PaymentOrderEntity
import com.sopp.Payment.service.PaymentOrderService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("payment/payment-order")
class PaymentOrderController(
    private val paymentOrderService: PaymentOrderService
) {

    @PostMapping("/{uuid}/customer/{customerId}")
    suspend fun createPaymentOrder(@PathVariable uuid: UUID, @PathVariable customerId: UUID): Boolean {
        return paymentOrderService.createPaymentOrder(uuid, customerId)
    }

    @GetMapping("/customer/{customerId}")
    suspend fun getPaymentsOfUser(@PathVariable customerId: UUID): List<PaymentOrderEntity> {
        return paymentOrderService.getPaymentsOfUser(customerId)
    }

    @GetMapping("/merchant/{merchantId}")
    suspend fun getPaymentsOfMerchant(@PathVariable merchantId: UUID): List<PaymentOrderEntity> {
        return paymentOrderService.getPaymentsOfMerchant(merchantId)
    }
}