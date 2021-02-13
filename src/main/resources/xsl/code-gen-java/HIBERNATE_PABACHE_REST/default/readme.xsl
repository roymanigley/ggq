<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
    <xsl:param name="PROJECT_NAME" select="'code-with-quarkus'"/>
    <xsl:template match="/">
# <xsl:value-of select="$PROJECT_NAME" />

## Getting Started

Run Application in `dev` mode
> uses h2 database

        ./mvnw quarkus:dev

> API Docs
        - Swagger [http://localhost:8080/swagger-ui](http://localhost:8080/swagger-ui)
        - OpenAPI [http://localhost:8080/q/openapi](http://localhost:8080/q/openapi)

Run unit tests

        ./mvnw clean test

Run unit tests and integration tests

        ./mvnw clean test -Pintegration-tests

> Check code coverage after integration test run `target/site/jacoco/index.html`
> Check code pmd report `./mvnw pmd:pmd` thech check `target/site/pmd.html`
    </xsl:template>
</xsl:stylesheet>