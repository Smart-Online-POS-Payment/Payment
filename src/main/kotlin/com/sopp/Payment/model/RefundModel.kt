package com.sopp.Payment.model

import com.sopp.Payment.entity.PaymentTransactionEntity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.math.BigDecimal
import java.util.UUID

data class RefundModel(
    val transactionId: UUID,
    val customerId: String?,
    val amount: BigDecimal,
    @Enumerated(EnumType.STRING)
    val category: PaymentTransactionModel.Category
){
    constructor(paymentTransactionEntity: PaymentTransactionEntity): this(paymentTransactionEntity.id, paymentTransactionEntity.customerId, paymentTransactionEntity.paymentAmount, paymentTransactionEntity.category)
}

