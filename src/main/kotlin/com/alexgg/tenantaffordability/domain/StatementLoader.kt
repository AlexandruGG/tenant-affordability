package com.alexgg.tenantaffordability.domain

import com.alexgg.tenantaffordability.infrastructure.CsvFileLoader
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import java.time.LocalDate
import javax.money.MonetaryAmount

@Component
class StatementLoader(private val mapper: CsvMapper, private val resourceLoader: ResourceLoader) : CsvFileLoader<Statement> {
    companion object {
        private const val HEADER_LINES = 11
        private val schema: CsvSchema =
            CsvSchema.builder()
                .addColumn("Date")
                .addColumn("Payment Type")
                .addColumn("Details")
                .addColumn("Money Out")
                .addColumn("Money In")
                .addColumn("Balance")
                .build()
    }

    override fun load(path: String): List<Statement> {
        val lines = resourceLoader.getResource(path).file.readLines().drop(HEADER_LINES).joinToString("\n")
        return mapper
            .readerFor(Statement::class.java)
            .with(schema)
            .readValues<Statement>(lines)
            .readAll()
    }
}

data class Statement(
    @field:JsonProperty("Date") @field:JsonFormat(pattern = "d['st']['nd']['rd']['th'] MMMM yyyy") val date: LocalDate,
    @field:JsonProperty("Payment Type") val paymentType: PaymentType,
    @field:JsonProperty("Details") val details: String,
    @field:JsonProperty("Money Out") val moneyOut: MonetaryAmount,
    @field:JsonProperty("Money In") val moneyIn: MonetaryAmount,
    @field:JsonProperty("Balance") val balance: MonetaryAmount,
)

enum class PaymentType(
    @field:JsonValue val value: String,
) {
    ATM("ATM"),
    DIRECT_DEBIT("Direct Debit"),
    CARD_PAYMENT("Card Payment"),
    BANK_CREDIT("Bank Credit"),
    STANDING_ORDER("Standing Order"),
    BANK_TRANSFER("Bank Transfer"),
}
