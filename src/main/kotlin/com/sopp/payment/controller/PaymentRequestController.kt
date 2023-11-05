package com.sopp.payment.controller

import com.sopp.payment.entity.PaymentRequestEntity
import com.sopp.payment.service.PaymentRequestService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("payment/payment-request")
class PaymentRequestController(
    private val paymentRequestService: PaymentRequestService
) {

    @PutMapping
    suspend fun createPaymentRequest(@RequestBody paymentRequestEntity: PaymentRequestEntity){
        paymentRequestService.createPaymentRequest(paymentRequestEntity)
    }

    @PostMapping("{uuid}/cancel")
    suspend fun cancelPaymentRequest(@PathVariable uuid: UUID){

    }

    @PostMapping("cancel")
    suspend fun cancelPaymentRequests(){

    }

    @GetMapping("{uuid}")
    suspend fun getPaymentRequestDetail(@PathVariable uuid: UUID){

    }
}