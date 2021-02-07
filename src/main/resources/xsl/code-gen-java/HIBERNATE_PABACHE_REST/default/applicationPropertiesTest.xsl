<xsl:stylesheet version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
<xsl:template match="/">quarkus.datasource.db-kind=h2
quarkus.datasource.username=username-default
quarkus.datasource.jdbc.url=jdbc:h2:file:./target/database-test
quarkus.datasource.jdbc.min-size=3
quarkus.datasource.jdbc.max-size=13
quarkus.hibernate-orm.database.generation=drop-and-create
    </xsl:template>
</xsl:stylesheet>