package com.sopp.Payment.client

import com.sopp.Payment.model.ResponseModel
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import java.math.BigDecimal

@Service
class WalletClient(
    @Qualifier("walletWebClient")
    val client: WebClient,
) {
    suspend fun withdrawMoney(
        customerId: String,
        amount: BigDecimal,
    ): ResponseModel {
        return client
            .delete()
            .uri("wallet/{customerId}/amount/{amount}", customerId, amount)
            .retrieve()
            .awaitBody()
    }

    suspend fun addMoney(
        merchantId: String,
        amount: BigDecimal,
    ): ResponseModel {
        return client
            .put()
            .uri("wallet/$merchantId/amount/$amount")
            .retrieve()
            .awaitBody()
    }
}
