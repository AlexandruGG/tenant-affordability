package com.alexgg.tenantaffordability.domain

import com.alexgg.tenantaffordability.infrastructure.CsvFileLoader
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import javax.money.MonetaryAmount

@Component
class PropertyLoader(private val mapper: CsvMapper, private val resourceLoader: ResourceLoader) : CsvFileLoader<Property> {
    companion object {
        private val schema: CsvSchema =
            CsvSchema.builder()
                .setUseHeader(true)
                .addColumn("Id")
                .addColumn("Address")
                .addColumn("Price (pcm)")
                .build()
    }

    override fun load(path: String): List<Property> =
        mapper
            .readerFor(Property::class.java)
            .with(schema)
            .readValues<Property>(resourceLoader.getResource(path).inputStream)
            .readAll()
}

data class Property(
    @field:JsonProperty("Id") val id: Int,
    @field:JsonProperty("Address") val address: String,
    @field:JsonProperty("Price (pcm)") val price: MonetaryAmount,
)
