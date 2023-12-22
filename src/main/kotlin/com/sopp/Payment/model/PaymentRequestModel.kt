package com.sopp.Payment.model

import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.math.BigDecimal

data class PaymentRequestModel(
    val merchantId: String,
    val paymentAmount: BigDecimal,
    @Enumerated(EnumType.STRING)
    val category: PaymentTransactionModel.Category,
    var paymentMessage: String?,
)
