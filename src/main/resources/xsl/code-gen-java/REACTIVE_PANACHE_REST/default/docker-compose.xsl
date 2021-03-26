<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" omit-xml-declaration="yes"></xsl:output>
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
    <xsl:param name="PROJECT_NAME" select="'code-with-quarkus'"/>
    <xsl:template match="/">version: '2.0'

services:
  postgres-db:
    image: postgres
    restart: always
    ports:
      - 5432:5432
    environment:
      POSTGRES_USERNAME: quarkus
      POSTGRES_PASSWORD: toor
      POSTGRES_DB: quarkus_test

    </xsl:template>
</xsl:stylesheet>
