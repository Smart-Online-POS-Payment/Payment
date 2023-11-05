package com.sopp.payment.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "payment-requests")
data class PaymentRequestEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long=0,
    var merchantId: UUID,
    var paymentAmount: Long,
    var paymentMessage: String
)
