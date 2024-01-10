package com.sopp.Payment.model

import java.math.BigDecimal
import java.util.*

data class WalletModel(
    val id: UUID,
    val customerId: String,
    val balance: BigDecimal,
)
