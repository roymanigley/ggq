<xsl:stylesheet version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:variable name="vLower" select=
            "'abcdefghijklmnopqrstuvwxyz'"/>

    <xsl:variable name="vUpper" select=
            "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'"/>

    <xsl:template name="getterAndSetter">
        <xsl:param name="name"/>
        <xsl:param name="type"/>
        <xsl:param name="entityType"/>
        <xsl:param name="nameUpper" select="concat(translate(substring($name,1,1), $vLower, $vUpper),
          substring($name, 2),
          substring(' ', 1 div not(position()=last()))
         )"/>
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

    <xsl:template name="toLowerCase">
        <xsl:param name="value"/>
        <xsl:value-of select="translate($value, $vUpper, $vLower)"/>
    </xsl:template>


    <xsl:template name="firstToLowerCase">
        <xsl:param name="value"/>
        <xsl:param name="valueNew" select="concat(translate(substring($value,1,1), $vUpper, $vLower),
          substring($value, 2),
          substring(' ', 1 div not(position()=last()))
         )"/><xsl:value-of select="$valueNew"/>
    </xsl:template>

    <xsl:template name="firstToUpperCase">
        <xsl:param name="value"/>
        <xsl:param name="valueNew" select="concat(translate(substring($value,1,1), $vLower, $vUpper),
          substring($value, 2),
          substring(' ', 1 div not(position()=last()))
         )"/><xsl:value-of select="$valueNew"/>
    </xsl:template>
</xsl:stylesheet>