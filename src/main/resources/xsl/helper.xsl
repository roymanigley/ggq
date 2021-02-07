<xsl:stylesheet version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:xalan="http://xml.apache.org/xalan"
        xmlns:ggq="xalan://ch.hftm.ggq.xsl.GGQ">

    <xsl:template name="getterAndSetter">
        <xsl:param name="name"/>
        <xsl:param name="type"/>
        <xsl:param name="entityType"/>
        <xsl:param name="nameUpper" select="ggq:firstToUpperCase($name)"/>
    public <xsl:value-of select="$type"/> get<xsl:value-of select="$nameUpper"/>() {
        return <xsl:value-of select="$name" />;
    }

    public void set<xsl:value-of select="$nameUpper"/>(<xsl:value-of select="concat($type, ' ', $name)" />) {
        this.<xsl:value-of select="$name" /> = <xsl:value-of select="$name" />;
    }

    public <xsl:value-of select="concat($entityType, ' ', $name)"/>(<xsl:value-of select="concat($type, ' ', $name)" />) {
        this.<xsl:value-of select="$name" /> = <xsl:value-of select="$name" />;
        return this;
    }
    </xsl:template>
</xsl:stylesheet>