package com.sopp.Payment.controller

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.sopp.Payment.entity.PaymentOrderEntity
import com.sopp.Payment.service.PaymentOrderService
import org.springframework.web.bind.annotation.*
import java.io.FileInputStream
import java.util.*


@RestController
@RequestMapping("payment/payment-order")
@CrossOrigin(origins = ["http://localhost:3000"])
class PaymentOrderController(
    private val paymentOrderService: PaymentOrderService
) {

    @PostMapping("/{uuid}/customer/{customerId}")
    suspend fun createPaymentOrder(@RequestHeader("Authorization") authorizationHeader: String, @PathVariable uuid: UUID, @PathVariable customerId: UUID): Boolean {
        val serviceAccount = FileInputStream("serviceAccountKey.json")
        val token = extractBearerToken(authorizationHeader)
        val options = FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl("https://sopp-72243-default-rtdb.europe-west1.firebasedatabase.app")
            .build()

        val firebaseApp = FirebaseApp.initializeApp(options)
        val firebaseAuth = FirebaseAuth.getInstance(firebaseApp)
        val tokenVerify = firebaseAuth.verifyIdToken(token)
        println("Token:${tokenVerify.email}")
        return paymentOrderService.createPaymentOrder(uuid, customerId)
    }

    @GetMapping("/customer/{customerId}")
    suspend fun getPaymentsOfUser(@PathVariable customerId: UUID): List<PaymentOrderEntity> {
        return paymentOrderService.getPaymentsOfUser(customerId)
    }

    @GetMapping("/merchant/{merchantId}")
    suspend fun getPaymentsOfMerchant(@PathVariable merchantId: UUID): List<PaymentOrderEntity> {
        return paymentOrderService.getPaymentsOfMerchant(merchantId)
    }

    private fun extractBearerToken(authorizationHeader: String?): String {
        // Check if the authorization header is present and starts with "Bearer "
        if (!authorizationHeader.isNullOrBlank() && authorizationHeader.startsWith("Bearer ")) {
            // Extract the token after "Bearer "
            return authorizationHeader.substringAfter("Bearer ").trim()
        }

        // If the header doesn't follow the expected format, you might want to handle this case accordingly.
        // You can throw an exception, return an empty string, or handle it based on your requirements.
        throw IllegalArgumentException("Invalid or missing Bearer token")
    }
}