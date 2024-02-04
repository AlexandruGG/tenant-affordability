package com.alexgg.tenantaffordability.infrastructure

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import org.javamoney.moneta.FastMoney
import java.math.BigDecimal
import javax.money.MonetaryAmount

class MonetaryAmountDeserializer : StdDeserializer<MonetaryAmount>(MonetaryAmount::class.java) {
    override fun deserialize(
        parser: JsonParser,
        context: DeserializationContext,
    ): MonetaryAmount {
        val amountString = parser.readValueAs(String::class.java)
        val currency = amountString.toCurrency()
        val cleanedAmount = amountString.replace(Regex("[^\\d.]"), "")
        val parsedAmount = cleanedAmount.toBigDecimalOrNull() ?: BigDecimal.ZERO
        return FastMoney.of(parsedAmount, currency)
    }
}

private fun String.toCurrency(): String =
    when {
        contains("£") -> "GBP"
        contains("$") -> "USD"
        else -> "GBP"
    }
