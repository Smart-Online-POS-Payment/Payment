package com.sopp.Payment.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "payment_requests")
data class PaymentRequestEntity(
    @Id
    val id: UUID=UUID.randomUUID(),
    var merchantId: String,
    var paymentAmount: Long,
    var paymentMessage: String
){
    constructor() : this(UUID.randomUUID(), UUID.randomUUID().toString(), 0L, "")
}
