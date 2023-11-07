package com.sopp.Payment.repository

import com.sopp.Payment.entity.PaymentRequestEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PaymentRequestRepository: CrudRepository<PaymentRequestEntity, UUID> {

}