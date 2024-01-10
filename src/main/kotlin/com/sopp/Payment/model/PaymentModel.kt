package com.sopp.Payment.model

import com.sopp.Payment.entity.PaymentTransactionEntity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.math.BigDecimal
import java.util.Date
import java.util.UUID

data class PaymentModel(
    val orderId: UUID,
    val amount: BigDecimal,
    val description: String?,
    @Enumerated(EnumType.STRING)
    val category: PaymentTransactionModel.Category,
    val date: Date,
) {
    constructor(
        paymentTransactionEntity: PaymentTransactionEntity,
    ) : this(
        paymentTransactionEntity.id,
        paymentTransactionEntity.paymentAmount,
        paymentTransactionEntity.paymentMessage,
        paymentTransactionEntity.category,
        paymentTransactionEntity.paymentDate!!,
    )
}
