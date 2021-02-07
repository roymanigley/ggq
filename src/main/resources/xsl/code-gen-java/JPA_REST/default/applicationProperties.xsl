<xsl:stylesheet version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
<xsl:template match="/">%dev.quarkus.datasource.db-kind=h2
%dev.quarkus.datasource.username=username-default
%dev.quarkus.datasource.jdbc.url=jdbc:h2:file:./target/database-dev
%dev.quarkus.datasource.jdbc.min-size=3
%dev.quarkus.datasource.jdbc.max-size=13
%dev.quarkus.hibernate-orm.database.generation=drop-and-create
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=root
quarkus.datasource.password=toor
quarkus.hibernate-orm.database.generation=update
quarkus.datasource.jdbc.url=jdbc:postgresql://postgres:3306/database
    </xsl:template>
</xsl:stylesheet>