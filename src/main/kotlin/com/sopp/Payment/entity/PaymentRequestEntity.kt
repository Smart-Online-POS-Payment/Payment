package com.sopp.Payment.entity

import com.sopp.Payment.model.TransactionType
import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "payment_requests")
data class PaymentRequestEntity(
    @Id
    val id: UUID=UUID.randomUUID(),
    var merchantId: String,
    var paymentAmount: BigDecimal,
    var paymentMessage: String
){
    constructor() : this(UUID.randomUUID(), UUID.randomUUID().toString(), BigDecimal.ONE, "")
}