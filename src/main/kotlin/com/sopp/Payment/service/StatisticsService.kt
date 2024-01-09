package com.sopp.Payment.service

import com.sopp.Payment.model.StatsModel
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class StatisticsService(
    private val paymentOrderService: PaymentOrderService
) {

    suspend fun calculateCategoricalExpenseRates(customerId: String, interval: Int): MutableMap<String, Double> {
        val currentDate = Date.from(Instant.now())
        val payments = paymentOrderService.getPaymentsOfUser(customerId)
            .filter { payment ->
                val dayDifference = (currentDate.time - payment.date.time) / (60*60*60*24)
                dayDifference.toInt() <= interval
            }
        val paymentDictionary = mutableMapOf<String, Double>()
        payments.forEach { payment ->
            val category = payment.category.toString()
            val amount = payment.amount.toDouble()
            paymentDictionary[category] = paymentDictionary[category] ?: 0.0
            paymentDictionary[category] = paymentDictionary[category]!! + amount
        }
        return paymentDictionary
    }

    suspend fun calculateIncomeAtInterval(merchantId: String, interval: Int): Double {
        val currentDate = Date.from(Instant.now())
        var weeklyPayment = 0.0
        paymentOrderService.getPaymentTransaction(merchantId).filter {
            val dayDifference = (currentDate.time - it.paymentDate!!.time) / 60
            dayDifference.toInt() <= interval
        }.forEach{
            weeklyPayment += it.paymentAmount.toDouble()
        }
        return weeklyPayment
    }

    suspend fun calculateCategoricalIncomeRates(merchantId: String, interval: Int): List<StatsModel> {
        val currentDate = Date.from(Instant.now())

        val payments = paymentOrderService.getPaymentsOfMerchant(merchantId)
            .filter {
                val dayDifference = (currentDate.time - it.date!!.time) / (60*60*60*24)
                dayDifference.toInt() <= interval
            }

        val paymentDictionary = mutableMapOf<String, Double>()
        payments.forEach { payment ->
            val category = payment.category.toString()
            val amount = payment.amount.toDouble()
            paymentDictionary[category] = paymentDictionary[category] ?: 0.0
            paymentDictionary[category] = paymentDictionary[category]!! + amount
        }
        return paymentDictionary.map { (category, amount) -> StatsModel(amount, category) }
    }

    suspend fun weeklyCustomerAmount(merchantId: String): Int {
        val currentDate = Date.from(Instant.now())
        val uniqueCustomers = HashSet<String>()

        paymentOrderService.getPaymentTransaction(merchantId)
            .filter {
                val dayDifference = (currentDate.time - it.paymentDate!!.time) / (1000 * 60 * 60 * 24)
                dayDifference <= 7
            }.forEach {
                uniqueCustomers.add(it.customerId!!)
            }
        return uniqueCustomers.size
    }
}