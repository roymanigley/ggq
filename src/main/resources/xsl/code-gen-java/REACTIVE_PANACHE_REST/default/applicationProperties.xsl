<xsl:stylesheet version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
<xsl:template match="/">quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=quarkus
quarkus.datasource.password=toor
quarkus.hibernate-orm.database.generation=create
quarkus.datasource.reactive.url=vertx-reactive:postgresql://localhost/quarkus_test

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
