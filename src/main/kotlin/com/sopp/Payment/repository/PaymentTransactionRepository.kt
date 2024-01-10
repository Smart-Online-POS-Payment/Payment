package com.sopp.Payment.repository

import com.sopp.Payment.entity.PaymentTransactionEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface PaymentTransactionRepository : CrudRepository<PaymentTransactionEntity, UUID> {
    fun findByCustomerId(customerId: String): List<PaymentTransactionEntity>

    fun findByMerchantId(merchantId: String): List<PaymentTransactionEntity>

    fun findByReference(reference: UUID): Optional<PaymentTransactionEntity>

    @Query(
        "SELECT p FROM PaymentTransactionEntity p WHERE p.merchantId = :merchantId " +
            "AND p.type = 'FinalizeSale'",
    )
    fun findFinalizeSalePaymentsByMerchantId(
        @Param("merchantId") merchantId: String,
    ): List<PaymentTransactionEntity>

    @Query(
        "SELECT p FROM PaymentTransactionEntity p WHERE p.customerId = :customerId " +
            "AND p.type = 'FinalizeSale'",
    )
    fun findFinalizeSalePaymentsByCustomerId(
        @Param("customerId") customerId: String,
    ): List<PaymentTransactionEntity>

    @Query(
        "SELECT p FROM PaymentTransactionEntity p WHERE p.merchantId = :merchantId " +
            "AND p.type = 'RequestRefund'",
    )
    fun findMerchantRefundRequests(
        @Param("merchantId") merchantId: String,
    ): List<PaymentTransactionEntity>
}
