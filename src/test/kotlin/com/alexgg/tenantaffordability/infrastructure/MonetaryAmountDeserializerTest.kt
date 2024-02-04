package com.alexgg.tenantaffordability.infrastructure

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import org.assertj.core.api.Assertions.assertThat
import org.javamoney.moneta.FastMoney
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import java.util.stream.Stream
import javax.money.MonetaryAmount

class MonetaryAmountDeserializerTest {
    private val sut: MonetaryAmountDeserializer = MonetaryAmountDeserializer()

    private val parser: JsonParser = mock()
    private val context: DeserializationContext = mock()

    companion object {
        @JvmStatic
        fun generateMoney(): Stream<Arguments> =
            Stream.of(
                Arguments.of("£120", FastMoney.of(120, "GBP")),
                Arguments.of("$120", FastMoney.of(120, "USD")),
                Arguments.of("120", FastMoney.of(120, "GBP")),
                Arguments.of("£120.45", FastMoney.of(120.45, "GBP")),
                Arguments.of("£", FastMoney.of(0, "GBP")),
                Arguments.of("", FastMoney.of(0, "GBP")),
            )
    }

    @ParameterizedTest
    @MethodSource("generateMoney")
    fun `should correctly deserialize money strings with similar formats`(
        raw: String,
        expected: MonetaryAmount,
    ) {
        given(parser.readValueAs(String::class.java)).willReturn(raw)

        val result = sut.deserialize(parser, context)
        assertThat(result).isEqualTo(expected)
    }
}
