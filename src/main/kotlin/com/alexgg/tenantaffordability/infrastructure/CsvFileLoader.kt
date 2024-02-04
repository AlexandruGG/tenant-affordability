package com.alexgg.tenantaffordability.infrastructure

interface CsvFileLoader<T> {
    fun load(path: String): List<T>
}
