<xsl:stylesheet version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
<xsl:template match="/">quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=quarkus
quarkus.datasource.password=toor
quarkus.datasource.reactive.url=postgresql://localhost:5432/quarkus_test
quarkus.hibernate-orm.database.generation=drop-and-create
</xsl:template>
</xsl:stylesheet>
