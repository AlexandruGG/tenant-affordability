package com.alexgg.tenantaffordability.domain

import org.javamoney.moneta.FastMoney
import org.springframework.stereotype.Component
import java.time.Month
import javax.money.Monetary

@Component
class AffordabilityCalculator {
    companion object {
        private const val AFFORDABILITY_RATIO = 1.25
    }

    fun compute(
        statements: List<Statement>,
        properties: List<Property>,
    ): List<Property> {
        val statementsByMonth = statements.groupBy { it.date.month }
        val excessIncome =
            computeExcessIncome(statementsByMonth.getRecurringIncomeByMonth(), statementsByMonth.getRecurringExpensesByMonth())

        return properties.filter { it.price.multiply(AFFORDABILITY_RATIO).isLessThan(excessIncome) }
    }

    private fun computeExcessIncome(
        incomeByMonth: Map<Month, FastMoney>,
        expensesByMonth: Map<Month, FastMoney>,
    ): FastMoney {
        val averageIncome = incomeByMonth.values.reduce { acc, it -> acc.add(it) }.divide(incomeByMonth.size)
        val averageExpenses = expensesByMonth.values.reduce { acc, it -> acc.add(it) }.divide(incomeByMonth.size)

        return averageIncome.subtract(averageExpenses)
    }
}

private fun Map<Month, List<Statement>>.getRecurringIncomeByMonth(): Map<Month, FastMoney> {
    val currency = this.values.firstOrNull()?.firstOrNull()?.moneyIn?.currency ?: Monetary.getCurrency("GBP")
    return mapValues {
        it.value.filter { st -> st.paymentType == PaymentType.BANK_CREDIT }
            .fold(FastMoney.of(0, currency)) { acc, st -> acc.add(st.moneyIn) }
    }
}

private fun Map<Month, List<Statement>>.getRecurringExpensesByMonth(): Map<Month, FastMoney> {
    val currency = this.values.firstOrNull()?.firstOrNull()?.moneyIn?.currency ?: Monetary.getCurrency("GBP")
    return mapValues {
        it.value.filter { st -> st.paymentType == PaymentType.DIRECT_DEBIT }
            .fold(FastMoney.of(0, currency)) { acc, st -> acc.add(st.moneyOut) }
    }
}
