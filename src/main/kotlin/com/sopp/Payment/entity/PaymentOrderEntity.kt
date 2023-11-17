package com.sopp.Payment.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.util.*

@Entity
@Table(name = "payment_orders")
data class PaymentOrderEntity(
    @Id
    val id: UUID = UUID.randomUUID(),
    var merchantId: String,
    var customerId: String,
    var paymentAmount: Long,
    var paymentMessage: String,
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    var paymentDate: Date?
){
    constructor(): this(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), 0L, "", Date())
}
