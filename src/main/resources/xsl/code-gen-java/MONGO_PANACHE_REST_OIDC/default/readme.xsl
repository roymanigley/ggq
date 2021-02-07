<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
    <xsl:param name="PROJECT_NAME" select="'code-with-quarkus'"/>
    <xsl:template match="/">
# <xsl:value-of select="$PROJECT_NAME" />

## Getting Started

Run Application in `dev` mode
> uses mongodb in `docker-compose`
1. Start the mongodb and keycloak container

        docker-compose -f src/main/docker/docker-compose.yml up -d

2. Setup Keycloak (Frontend, without client_secret)

    - Login to admin panel http://localhost:9999 (root: toor)
    - create a realm `experiment-oidc`
    - create a client `web-app-frontend-client`
        - **on the client set the `Valid Redirect URIs` to `*`**
        - **on the client set the `Access Type ` to `Public`**
    - create a user and set password (dev: dev)

3. Setup Keycloak (m2m, with client_secret)
> otherwise you even improve your security by adding a client_secret ans wirh out aauto redirect

    - Login to admin panel http://localhost:9999 (root: toor)
    - create a realm `experiment-oidc`
    - create a client `web-app-m2m-client`
        - **on the client set the `Valid Redirect URIs` to `*`**
        - **on the client set the `Access Type ` to `Confidential`**
    - copy the `Secret` form the credentials add add it to the `application.properties` file a user and set password (dev: dev)

4. Start the Quarkus application

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
