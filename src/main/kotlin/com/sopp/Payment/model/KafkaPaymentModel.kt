package com.sopp.Payment.model

import com.sopp.Payment.entity.PaymentOrderEntity
import java.math.BigDecimal

data class KafkaPaymentModel(
    val customerId: String,
    val merchantId: String,
    val amount: BigDecimal,
    val paymentMessage: String
){
    constructor(paymentOrderEntity: PaymentOrderEntity):this(customerId=paymentOrderEntity.customerId, merchantId = paymentOrderEntity.merchantId, amount=paymentOrderEntity.paymentAmount, paymentMessage=paymentOrderEntity.paymentMessage)
}
