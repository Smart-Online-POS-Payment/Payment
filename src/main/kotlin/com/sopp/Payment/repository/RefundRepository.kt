package com.sopp.Payment.repository

import com.sopp.Payment.entity.RefundEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface RefundRepository: CrudRepository<RefundEntity, UUID> {
    fun findByCustomerId(customerId: String): List<RefundEntity>

    fun findByMerchantId(merchantId: String): List<RefundEntity>
    fun findByOrderId(orderId: UUID): Optional<RefundEntity>
}