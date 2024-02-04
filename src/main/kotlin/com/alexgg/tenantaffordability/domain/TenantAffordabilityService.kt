package com.alexgg.tenantaffordability.domain

import com.alexgg.tenantaffordability.infrastructure.CsvFileLoader
import org.springframework.stereotype.Service

@Service
class TenantAffordabilityService(
    private val statementLoader: CsvFileLoader<Statement>,
    private val propertyLoader: CsvFileLoader<Property>,
    private val affordabilityCalculator: AffordabilityCalculator,
) {
    fun compute() =
        affordabilityCalculator.compute(
            statementLoader.load("/bank_statement.csv"),
            propertyLoader.load("/properties.csv"),
        )
}
