<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:ggq="xalan://ch.hftm.ggq.xsl.GGQ">
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="PARAM_01"></xsl:param>
    <xsl:template match="/">ggq:firstToUpperCase=<xsl:value-of select="ggq:firstToUpperCase($PARAM_01)" />
ggq:firstToLowerCase=<xsl:value-of select="ggq:firstToLowerCase($PARAM_01)" />
ggq:toUpperCase=<xsl:value-of select="ggq:toUpperCase($PARAM_01)" />
ggq:toLowerCase=<xsl:value-of select="ggq:toLowerCase($PARAM_01)" />
</xsl:template>

</xsl:stylesheet>
