<xsl:stylesheet version="1.0"
        xmlns:xalan="http://xml.apache.org/xalan"
        xmlns:ggq="xalan://ch.hftm.ggq.xsl.GGQ"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
    <xsl:param name="PROJECT_NAME" select="'code-with-quarkus'"/>
<xsl:template match="/">
# configure the MongoDB client for a replica set of two nodes
quarkus.mongodb.connection-string = mongodb://root:toor@localhost:27017
# mandatory if you don't specify the name of the database using @MongoEntity
quarkus.mongodb.database = <xsl:value-of select="ggq:toLowerCase($PROJECT_NAME)" />

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
