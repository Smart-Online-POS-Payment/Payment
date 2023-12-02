package com.sopp.Payment.service

import com.sopp.Payment.client.WalletClient
import com.sopp.Payment.model.ResponseModel
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class WalletService(
    private val walletClient: WalletClient
) {
    suspend fun withdrawMoney(customerId: String, amount: BigDecimal): ResponseModel {
        return walletClient.withdrawMoney(customerId, amount)
    }

    suspend fun addMoney(merchantId: String, amount: BigDecimal): ResponseModel{
        return walletClient.addMoney(merchantId, amount)
    }
}