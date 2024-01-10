package com.sopp.Payment.entity

import com.sopp.Payment.model.PaymentRequestModel
import com.sopp.Payment.model.PaymentTransactionModel
import com.sopp.Payment.model.PaymentTransactionModel.Type
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.math.BigDecimal
import java.time.Instant
import java.util.*

@Entity
@Table(name = "payment_transaction")
data class PaymentTransactionEntity(
    @Id
    val id: UUID,
    val merchantId: String,
    var customerId: String?,
    val paymentAmount: BigDecimal,
    @Enumerated(EnumType.STRING)
    val category: PaymentTransactionModel.Category,
    @Enumerated(EnumType.STRING)
    var type: Type,
    var paymentMessage: String?,
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    var paymentDate: Date?,
    var reference: UUID?,
) {
    constructor() : this(UUID.randomUUID(), "", null, BigDecimal.ZERO, PaymentTransactionModel.Category.Other, PaymentTransactionModel.Type.RequestSale, null, null, null)
    constructor(
        paymentRequestModel: PaymentRequestModel,
    ) : this(UUID.randomUUID(), paymentRequestModel.merchantId, null, paymentRequestModel.paymentAmount, paymentRequestModel.category, Type.RequestSale, paymentRequestModel.paymentMessage, null, null)

    constructor(paymentTransactionEntity: PaymentTransactionEntity, reference: UUID?) : this(
        UUID.randomUUID(), paymentTransactionEntity.merchantId, paymentTransactionEntity.customerId, paymentTransactionEntity.paymentAmount, paymentTransactionEntity.category, Type.RequestRefund, paymentTransactionEntity.paymentMessage,
        Date.from(
            Instant.now(),
        ),
        reference,
    )
}
