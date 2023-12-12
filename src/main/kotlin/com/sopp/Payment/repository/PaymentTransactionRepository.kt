package com.sopp.Payment.repository

import com.sopp.Payment.entity.PaymentTransactionEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface PaymentTransactionRepository: CrudRepository<PaymentTransactionEntity, UUID> {

    fun findByCustomerId(customerId: String): List<PaymentTransactionEntity>

    fun findByMerchantId(merchantId: String): List<PaymentTransactionEntity>

    fun findByReference(reference: UUID): Optional<PaymentTransactionEntity>

}