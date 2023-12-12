package com.sopp.Payment.model

import com.sopp.Payment.entity.PaymentTransactionEntity
import java.math.BigDecimal
import java.util.Date

data class PaymentModel(
    val amount: BigDecimal,
    val description: String?,
    val date: Date
){
    constructor(paymentTransactionEntity: PaymentTransactionEntity): this(paymentTransactionEntity.paymentAmount, paymentTransactionEntity.paymentMessage, paymentTransactionEntity.paymentDate!!)
}
