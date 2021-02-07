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

quarkus.oidc.auth-server-url=http://localhost:9999/auth/realms/experiment-oidc
quarkus.http.auth.permission.authenticated.paths=/*
quarkus.http.auth.permission.authenticated.policy=authenticated

# OIDC Configuration Frontend
quarkus.oidc.client-id=web-app-frontend-client
quarkus.oidc.application-type=web-app
quarkus.keycloak.policy-enforcer.enable=true

# OIDC Configuration m2m
# quarkus.oidc.client-id=web-app-m2m-client
# quarkus.oidc.credentials.secret=&lt;your client secret&gt;
# quarkus.oidc.tls.verification=none


# Enable Policy Enforcement

    </xsl:template>
</xsl:stylesheet>
