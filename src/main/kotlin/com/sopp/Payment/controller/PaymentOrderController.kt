package com.sopp.Payment.controller


import com.sopp.Payment.entity.PaymentOrderEntity
import com.sopp.Payment.service.FirebaseService
import com.sopp.Payment.service.PaymentOrderService
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("payment/payment-order")
@CrossOrigin(origins = ["http://localhost:3000"])
class PaymentOrderController(
    private val paymentOrderService: PaymentOrderService,
    private val firebaseService: FirebaseService
) {

    @PostMapping("/{uuid}/customer/{customerId}")
    suspend fun createPaymentOrder(@RequestHeader("Authorization") authorizationHeader: String, @PathVariable uuid: UUID, @PathVariable customerId: UUID): Boolean {
        val isValid = firebaseService.validateUserToken(authorizationHeader, uuid.toString())

        if (isValid){
            return paymentOrderService.createPaymentOrder(uuid, customerId)
        }

        return false
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