# Tenant Affordability Calculator

A service that runs an affordability check against a list of properties, taking into account rent, income, and expenses.

Full requirements are described in [AffordabilityCheckTest.pdf](docs/AffordabilityCheckTest.pdf).

CSV files for the bank statements and list of properties considered are stored as resources in the application.

See assumptions in [Assumptions.md](docs/Assumptions.md).

## Usage

The application requires JDK 21 and will output the list of affordable properties when run:

- using the Gradle Wrapper: `./gradlew bootRun`
- using the pre-build jar: `java -jar tenantAffordability-1.0.0.jar`

To build use `./gradlew build`.

## Main Dependencies

- Kotlin 1.9 (JDK 21)
- Spring Boot 3.2
- Gradle 8.5
- Jackson (csv, date, money parsing)
