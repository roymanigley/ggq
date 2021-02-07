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

quarkus.http.auth.basic=true
quarkus.security.users.embedded.realm-name=properties-realm
quarkus.security.users.embedded.enabled=true
quarkus.security.users.embedded.plain-text=false
quarkus.security.users.embedded.algorithm=digest-md5
# echo -n username:properties-realm:password | md5sum
# admin:properties-realm:admin; user:properties-realm:user
quarkus.security.users.embedded.users.admin=416e79f9ada26bb0162b725626e3ef51
quarkus.security.users.embedded.users.user=adbc71193db227570b904d73b252c616
quarkus.security.users.embedded.roles.admin=ADMIN,USER
quarkus.security.users.embedded.roles.user=USER

# quarkus.http.auth.permission.authenticated.paths=/*
# quarkus.http.auth.permission.authenticated.policy=authenticated
    </xsl:template>
</xsl:stylesheet>
