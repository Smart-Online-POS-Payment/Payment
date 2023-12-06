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
    private val paymentRequestService: PaymentRequestService
) {

    @PostMapping
    suspend fun createPaymentRequest(@RequestBody paymentRequestEntity: PaymentRequestEntity): UUID? {
        return paymentRequestService.createPaymentRequest(paymentRequestEntity)
    }

    @DeleteMapping("{merchantId}/cancel/{uuid}")
    suspend fun cancelPaymentRequest(@PathVariable merchantId: String, @PathVariable uuid: UUID){
         paymentRequestService.cancelPaymentRequest(uuid)
    }

    @DeleteMapping("cancel/{merchantId}")
    suspend fun cancelPaymentRequests(@PathVariable merchantId: String){
        paymentRequestService.cancelPaymentRequests()
    }

    @GetMapping("{uuid}/customer/{customerId}")
    suspend fun getPaymentRequestDetail(@PathVariable uuid: UUID, @PathVariable customerId: String): PaymentRequestEntity? {
        return paymentRequestService.getPaymentRequestDetail(uuid)
    }
}