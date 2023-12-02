package com.sopp.Payment.model

import com.sopp.Payment.entity.PaymentOrderEntity
import java.math.BigDecimal
import java.util.Date

data class PaymentModel(
    val amount: BigDecimal,
    val description: String,
    val date: Date
){
    constructor(paymentOrderEntity: PaymentOrderEntity): this(paymentOrderEntity.paymentAmount, paymentOrderEntity.paymentMessage, paymentOrderEntity.paymentDate!!)
}
