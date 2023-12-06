package com.sopp.Payment.config.producer

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.sopp.Payment.entity.PaymentOrderEntity
import com.sopp.Payment.model.KafkaPaymentModel
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>
) {
    private val jsonMapper = ObjectMapper().apply {
        registerKotlinModule()
    }
    fun sendStringMessage(payment: PaymentOrderEntity) {
        val message = jsonMapper.writeValueAsString(KafkaPaymentModel(payment))
        kafkaTemplate.send("payment", message)
    }
}