package com.sopp.Payment.repository

import com.sopp.Payment.entity.PaymentOrderEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.util.UUID

@Repository
interface PaymentOrderRepository: CrudRepository<PaymentOrderEntity, UUID> {
    fun findByCustomerId(customerId: String): List<PaymentOrderEntity>

    fun findByMerchantId(merchantId: String): List<PaymentOrderEntity>
}