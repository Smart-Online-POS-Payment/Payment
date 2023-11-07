package com.sopp.Payment.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "payment_orders")
data class PaymentOrderEntity(
    @Id
    val id: UUID = UUID.randomUUID(),
    var merchantId: UUID,
    var customerId: UUID,
    var paymentAmount: Long,
    var paymentMessage: String
){
    constructor(): this(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 0L, "")
}
