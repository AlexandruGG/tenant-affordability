package com.alexgg.tenantaffordability

import com.alexgg.tenantaffordability.domain.TenantAffordabilityService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TenantAffordabilityApplication(val service: TenantAffordabilityService) : CommandLineRunner {
    override fun run(vararg args: String) = service.run()
}

fun main(args: Array<String>) {
    runApplication<TenantAffordabilityApplication>(*args)
}
