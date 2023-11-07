package com.sopp.Payment.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "payment-requests")
data class PaymentRequestEntity(
    @Id
    val id: UUID=UUID.randomUUID(),
    var merchantId: UUID,
    var paymentAmount: Long,
    var paymentMessage: String
){
    constructor() : this(UUID.randomUUID(), UUID.randomUUID(), 0L, "")
}
