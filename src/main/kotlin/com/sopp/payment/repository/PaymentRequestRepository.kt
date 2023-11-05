package com.sopp.payment.repository

import com.sopp.payment.entity.PaymentRequestEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentRequestRepository: CrudRepository<PaymentRequestEntity, Long> {

}