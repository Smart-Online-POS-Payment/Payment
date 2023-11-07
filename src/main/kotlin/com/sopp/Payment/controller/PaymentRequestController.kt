package com.sopp.Payment.controller

import com.sopp.Payment.entity.PaymentRequestEntity
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
    suspend fun createPaymentRequest(@RequestBody paymentRequestEntity: PaymentRequestEntity): UUID {
        return paymentRequestService.createPaymentRequest(paymentRequestEntity)
    }

    @DeleteMapping("{uuid}/cancel")
    suspend fun cancelPaymentRequest(@PathVariable uuid: UUID){
        paymentRequestService.cancelPaymentRequest(uuid)
    }

    @DeleteMapping("cancel")
    suspend fun cancelPaymentRequests(){
        paymentRequestService.cancelPaymentRequests()
    }

    @GetMapping("{uuid}")
    suspend fun getPaymentRequestDetail(@PathVariable uuid: UUID): PaymentRequestEntity {
        return paymentRequestService.getPaymentRequestDetail(uuid)
    }
}