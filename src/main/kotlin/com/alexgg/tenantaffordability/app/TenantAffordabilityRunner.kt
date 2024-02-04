package com.alexgg.tenantaffordability.app

import com.alexgg.tenantaffordability.domain.TenantAffordabilityService
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class TenantAffordabilityRunner(val service: TenantAffordabilityService) : CommandLineRunner {
    override fun run(vararg args: String) =
        with(service.compute()) {
            println("### Affordable Properties ###")
            println(joinToString("\n"))
        }
}
