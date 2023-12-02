package com.sopp.Payment.controller

import com.sopp.Payment.entity.PaymentRequestEntity
import com.sopp.Payment.service.FirebaseService
import com.sopp.Payment.service.PaymentRequestService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("payment/payment-request")
@CrossOrigin(origins = ["http://localhost:3000"])
class PaymentRequestController(
    private val paymentRequestService: PaymentRequestService,
    private val firebaseService: FirebaseService
) {

    @PostMapping
    suspend fun createPaymentRequest(@RequestBody paymentRequestEntity: PaymentRequestEntity): UUID? {
        val isValid = true
        if (isValid){
            return paymentRequestService.createPaymentRequest(paymentRequestEntity)
        }
        return null
    }

    @DeleteMapping("{merchantId}/cancel/{uuid}")
    suspend fun cancelPaymentRequest(@RequestHeader("Authorization") authorizationHeader: String, @PathVariable merchantId: String, @PathVariable uuid: UUID){
        val isValid = firebaseService.validateUserToken(authorizationHeader, merchantId)
        if (isValid){
            paymentRequestService.cancelPaymentRequest(uuid)
        }
    }

    @DeleteMapping("cancel/{merchantId}")
    suspend fun cancelPaymentRequests(@RequestHeader("Authorization") authorizationHeader: String, @PathVariable merchantId: String){
        val isValid = firebaseService.validateUserToken(authorizationHeader, merchantId)
        if (isValid){
            paymentRequestService.cancelPaymentRequests()
        }
    }

    @GetMapping("{uuid}/customer/{customerId}")
    suspend fun getPaymentRequestDetail(@RequestHeader("Authorization") authorizationHeader: String, @PathVariable uuid: UUID, @PathVariable customerId: String): PaymentRequestEntity? {
        val isValid = firebaseService.validateUserToken(authorizationHeader, customerId)
        if (isValid)
            return paymentRequestService.getPaymentRequestDetail(uuid)
        return null
    }

    @PostMapping("/refund/{uuid}")
    suspend fun createRefundRequest(@PathVariable uuid: UUID){

    }
}