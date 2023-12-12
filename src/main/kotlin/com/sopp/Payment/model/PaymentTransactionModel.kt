package com.sopp.Payment.model

import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.math.BigDecimal

data class PaymentTransactionModel(
    var merchantId: String,
    var customerId: String?,
    var paymentAmount: BigDecimal,
    @Enumerated(EnumType.STRING)
    var category: Category,
    @Enumerated(EnumType.STRING)
    val type: Type,
    var paymentMessage: String?
){
    enum class Type {
        RequestSale,
        FinalizeSale,
        RequestRefund,
        FinalizeRefund
    }

    enum class Category {
        Groceries,
        Clothing,
        Electronics,
        Tickets,
        CarRentals,
        Restaurants,
        Coffee,
        Charity,
        Rent,
        Gaming,
        Other
    }
}
