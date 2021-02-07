<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" omit-xml-declaration="yes"></xsl:output>
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
    <xsl:param name="PROJECT_NAME" select="'code-with-quarkus'"/>
    <xsl:template match="/">version: '2.0'

services:
  mongo-db:
    image: mongo
    restart: always
    ports:
      - 27017:27017
    environment:
      MONGO_INITDB_ROOT_USERNAME: root
      MONGO_INITDB_ROOT_PASSWORD: toor
      MONGO_INITDB_DATABASE: example
  keycloak:
    image: quay.io/keycloak/keycloak:12.0.3
    ports:
      - 9999:8080
      - 9443:8443
    environment:
      KEYCLOAK_USER: root
      KEYCLOAK_PASSWORD: toor
</xsl:template>
</xsl:stylesheet>
