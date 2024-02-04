package com.alexgg.tenantaffordability.infrastructure

import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvParser
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.money.MonetaryAmount

@Configuration
class MapperConfig {
    @Bean
    fun csvMapper(): CsvMapper =
        CsvMapper.builder()
            .enable(CsvParser.Feature.TRIM_SPACES)
            .enable(CsvParser.Feature.SKIP_EMPTY_LINES)
            .findAndAddModules()
            .addModule(SimpleModule().addDeserializer(MonetaryAmount::class.java, MonetaryAmountDeserializer()))
            .build()
}
