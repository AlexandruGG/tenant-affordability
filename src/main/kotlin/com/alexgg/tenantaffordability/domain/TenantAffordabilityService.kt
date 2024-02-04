package com.alexgg.tenantaffordability.domain

import com.alexgg.tenantaffordability.infrastructure.CsvFileLoader
import org.springframework.stereotype.Service

@Service
class TenantAffordabilityService(
    private val statementLoader: CsvFileLoader<Statement>,
    private val propertyLoader: CsvFileLoader<Property>,
    private val affordabilityCalculator: AffordabilityCalculator,
) {
    fun run() {
        val statements = statementLoader.load("/bank_statement.csv")
        val properties = propertyLoader.load("/properties.csv")

        val affordableProperties = affordabilityCalculator.compute(statements, properties)
        println(affordableProperties)
    }
}
