package com.sopp.Payment.controller

import com.sopp.Payment.service.StatisticsService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("payment/statistics")
@CrossOrigin(origins = ["http://localhost:3000"])
class PaymentStatisticsController(
    val statisticsService: StatisticsService
) {

    @GetMapping("/expenses/customer/{customerId}/category")
    suspend fun getCustomerExpensesPerCategory(@PathVariable customerId: String, @RequestBody interval: Int = 7): MutableMap<String, Double> {
        return statisticsService.calculateCategoricalExpenseRates(customerId, interval)
    }

    @GetMapping("/income/merchant/{merchantId}/category")
    suspend fun getMerchantIncomePerCategory(@PathVariable merchantId: String, @RequestBody interval: Int = 7): MutableMap<String, Double> {
        return statisticsService.calculateCategoricalIncomeRates(merchantId, interval)
    }

    @GetMapping("/merchant/{merchantId}/unique-customer")
    suspend fun getUniqueCustomers(@PathVariable merchantId: String, @RequestBody interval: Int=7): Int {
        return statisticsService.weeklyCustomerAmount(merchantId)
    }

    @GetMapping("/merchant/{merchantId}/income")
    suspend fun getIncome(@PathVariable merchantId: String, @RequestBody interval: Int=7): Double {
        return statisticsService.calculateIncomeAtInterval(merchantId, interval)
    }
}