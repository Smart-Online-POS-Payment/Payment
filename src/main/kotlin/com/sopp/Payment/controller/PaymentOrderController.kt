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
    suspend fun createPaymentOrder(@RequestHeader("Authorization") authorizationHeader: String, @PathVariable uuid: UUID, @PathVariable customerId: String): Boolean {
        val isValid = firebaseService.validateUserToken(authorizationHeader, customerId)

        if (isValid){
            return paymentOrderService.createPaymentOrder(uuid, customerId)
        }

        return false
    }

    @GetMapping("/customer/{customerId}")
    suspend fun getPaymentsOfUser(@RequestHeader("Authorization") authorizationHeader: String, @PathVariable customerId: String): List<PaymentOrderEntity> {
        val isValid = firebaseService.validateUserToken(authorizationHeader, customerId)
        if (isValid){
            return paymentOrderService.getPaymentsOfUser(customerId)
        }
        return emptyList()
    }

    @GetMapping("/merchant/{merchantId}")
    suspend fun getPaymentsOfMerchant(@RequestHeader("Authorization") authorizationHeader: String, @PathVariable merchantId: String): List<PaymentOrderEntity> {
        val isValid = firebaseService.validateUserToken(authorizationHeader, merchantId)

        if (isValid){
            return paymentOrderService.getPaymentsOfMerchant(merchantId)
        }
        return emptyList()
    }
}