package com.sopp.Payment.controller


import com.sopp.Payment.entity.PaymentTransactionEntity
import com.sopp.Payment.model.PaymentModel
import com.sopp.Payment.model.ResponseModel
import com.sopp.Payment.service.PaymentOrderService
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("payment/payment-order")
@CrossOrigin(origins = ["http://localhost:3000"])
class PaymentOrderController(
    private val paymentOrderService: PaymentOrderService,
) {

    @PostMapping("/{uuid}/customer/{customerId}")
    suspend fun createPaymentOrder(@PathVariable uuid: UUID, @PathVariable customerId: String): ResponseModel {
        return paymentOrderService.createPaymentOrder(uuid, customerId)
    }

    @GetMapping("/customer/{customerId}")
    suspend fun getPaymentsOfUser( @PathVariable customerId: String): List<PaymentModel> {
        return paymentOrderService.getPaymentsOfUser(customerId)
    }

    @GetMapping("/merchant/{merchantId}")
    suspend fun getPaymentsOfMerchant(@PathVariable merchantId: String): List<PaymentTransactionEntity> {
        return paymentOrderService.getPaymentsOfMerchant(merchantId)
    }
}