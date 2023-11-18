package com.sopp.Payment.client

import com.sopp.Payment.model.ResponseModel
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import java.math.BigDecimal

@Service
class WalletClient(
    @Qualifier("walletWebClient")
    val client: WebClient
) {
    suspend fun withdrawMoney(customerId: String, amount: BigDecimal): ResponseModel {

        return client
            .delete()
            .uri("wallet/{customerId}/amount/{amount}",customerId, amount)
            .retrieve()
            .awaitBody()
    }
}