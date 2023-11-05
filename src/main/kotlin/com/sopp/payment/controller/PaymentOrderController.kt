package com.sopp.payment.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("payment/payment-order")
class PaymentOrderController {

    @PostMapping("/{uuid}")
    suspend fun createPaymentOrder(@PathVariable uuid: UUID){

    }

    @GetMapping("/user/{userId}")
    suspend fun getPaymentsOfUser(@PathVariable userId: UUID){

    }
}