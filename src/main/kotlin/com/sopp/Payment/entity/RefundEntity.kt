package com.sopp.Payment.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "refund")
data class RefundEntity(
    @Id
    val id: UUID=UUID.randomUUID(),
    val orderId: UUID,
    val customerId: String,
    val merchantId: String,
    var completed: Boolean = false
) {
    constructor() : this(UUID.randomUUID(), UUID.randomUUID(),"","", false)
}
