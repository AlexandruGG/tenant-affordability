package com.alexgg.tenantaffordability.domain

import org.assertj.core.api.Assertions.assertThat
import org.javamoney.moneta.FastMoney
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TenantAffordabilityServiceIT {
    @Autowired
    private lateinit var sut: TenantAffordabilityService

    companion object {
        private val RECURRING_INCOME =
            FastMoney.of(((12.5 + 6 + 200 + 42.25 + 1000) + (12.5 + 6 + 200 + 42.25 + 1000)) / 2, "GBP")
        private val RECURRING_EXPENSES =
            FastMoney.of(((95 + 32.5 + 8.5 + 75 + 12.12 + 4.5 + 60) + (95 + 32.5 + 8.5 + 75 + 12.12 + 4.5 + 60)) / 2, "GBP")

        private val EXCESS_INCOME = RECURRING_INCOME.subtract(RECURRING_EXPENSES)
        private val MAX_PROPERTY_PRICE = EXCESS_INCOME.divide(1.25)
    }

    @Test
    fun `should return the correct list of affordable properties`() {
        println("### Calculations ###")
        println("Excess income: $EXCESS_INCOME")
        println("Max property price: $MAX_PROPERTY_PRICE")

        val result = sut.compute()
        assertThat(result)
            .hasSize(9)
            .allSatisfy { it.price.isLessThanOrEqualTo(MAX_PROPERTY_PRICE) }
    }
}
