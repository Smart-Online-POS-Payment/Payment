package com.sopp.Payment.model

import com.sopp.Payment.entity.PaymentTransactionEntity
import java.math.BigDecimal

data class KafkaPaymentModel(
    val customerId: String,
    val merchantId: String,
    val amount: BigDecimal,
    val paymentMessage: String?
){
    constructor(paymentTransactionEntity: PaymentTransactionEntity):this(customerId = paymentTransactionEntity.customerId!!, merchantId = paymentTransactionEntity.merchantId, amount =paymentTransactionEntity.paymentAmount, paymentMessage =paymentTransactionEntity.paymentMessage)
}
